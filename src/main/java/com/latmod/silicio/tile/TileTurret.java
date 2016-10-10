package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.lib.math.MathHelperLM;
import com.feed_the_beast.ftbl.lib.tile.TileLM;
import com.feed_the_beast.ftbl.lib.util.LMNBTUtils;
import com.feed_the_beast.ftbl.lib.util.LMServerUtils;
import com.latmod.silicio.SilSounds;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockDirectional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Created by LatvianModder on 03.03.2016.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileTurret extends TileLM implements ITickable
{
    public static final AxisAlignedBB[] SCAN_AREAS = MathHelperLM.getRotatedBoxes(new AxisAlignedBB(-5D, 0D, -5D, 6D, 10D, 6D));
    public byte cooldown = 0;
    public Entity target = null;
    public AxisAlignedBB scanArea;

    @Override
    public void onLoad()
    {
        super.onLoad();
        System.out.println("EffectiveSide: " + FMLCommonHandler.instance().getEffectiveSide() + ", World: " + worldObj);
    }

    @Override
    public void writeTileData(NBTTagCompound nbt)
    {
        super.writeTileData(nbt);
        nbt.setByte("Cooldown", cooldown);

        if(target != null)
        {
            LMNBTUtils.setUUID(nbt, "Target", target.getUniqueID(), true);
        }
    }

    @Override
    public void readTileData(NBTTagCompound nbt)
    {
        super.readTileData(nbt);
        cooldown = nbt.getByte("Cooldown");
        target = LMServerUtils.getEntityByUUID(worldObj, LMNBTUtils.getUUID(nbt, "Target", true));
    }

    @Override
    public void writeTileClientData(NBTTagCompound nbt)
    {
        super.writeTileClientData(nbt);
        if(target != null)
        {
            nbt.setInteger("EID", target.getEntityId());
        }
    }

    @Override
    public void readTileClientData(NBTTagCompound nbt)
    {
        super.readTileClientData(nbt);
        target = nbt.hasKey("EID") ? worldObj.getEntityByID(nbt.getInteger("EID")) : null;
        updateScanArea();
    }

    public void updateScanArea()
    {
        scanArea = SCAN_AREAS[getBlockState().getValue(BlockDirectional.FACING).ordinal()].addCoord(pos.getX(), pos.getY(), pos.getZ());
    }

    private void searchForTarget()
    {
        updateScanArea();

        target = null;

        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.5D;
        double z = pos.getZ() + 0.5D;
        double distSq;
        double prevDistSq = 0D;

        for(Entity e : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, scanArea))
        {
            if(!e.isDead && !(e instanceof EntityPlayer))
            {
                distSq = e.getDistanceSq(x, y, z);

                if(distSq <= 64D && (target == null || (distSq < prevDistSq)))
                {
                    target = e;
                    prevDistSq = distSq;
                }
            }
        }
    }

    @Override
    public void update()
    {
        if(isServerSide())
        {
            if(cooldown > 0)
            {
                cooldown--;
            }

            if(cooldown == 0)
            {
                boolean hasTarget = target != null;

                if(target != null)
                {
                    if(target.isDead)
                    {
                        searchForTarget();

                        if(target != null)
                        {
                            markDirty();
                        }
                    }
                    else
                    {
                        target.attackEntityFrom(DamageSource.lightningBolt, 8F);
                        playSound(SilSounds.TURRET_LOOP, SoundCategory.BLOCKS, 0.8F, 1F);
                        cooldown = 20;
                    }
                }
                else
                {
                    searchForTarget();
                    if(target == null)
                    {
                        cooldown = 40;
                    }
                }

                if(hasTarget != (target != null))
                {
                    if(target != null)
                    {
                        playSound(SilSounds.TURRET_START, SoundCategory.BLOCKS, 0.8F, 1F);
                    }
                    else
                    {
                        playSound(SilSounds.TURRET_END, SoundCategory.BLOCKS, 0.8F, 1F);
                    }

                    markDirty();
                }
            }
        }

        checkIfDirty();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        if(target == null)
        {
            return super.getRenderBoundingBox();
        }
        else if(scanArea == null)
        {
            updateScanArea();
        }
        return scanArea;
    }
}
