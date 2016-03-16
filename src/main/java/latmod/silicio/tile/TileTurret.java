package latmod.silicio.tile;

import ftb.lib.*;
import ftb.lib.api.tile.TileLM;
import latmod.silicio.block.BlockTurret;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileTurret extends TileLM
{
	public byte cooldown = 0;
	public Entity target = null;
	public AxisAlignedBB scanArea;
	
	public void writeTileData(NBTTagCompound tag)
	{
		tag.setByte("Cooldown", cooldown);
		
		if(target != null)
		{
			LMNBTUtils.setUUID(tag, "Target", target.getUniqueID(), true);
		}
	}
	
	public void readTileData(NBTTagCompound tag)
	{
		cooldown = tag.getByte("Cooldown");
		target = FTBLib.getEntityByUUID(worldObj, LMNBTUtils.getUUID(tag, "Target", true));
	}
	
	public void writeTileClientData(NBTTagCompound tag)
	{
		if(target != null)
		{
			tag.setInteger("EID", target.getEntityId());
		}
	}
	
	public void readTileClientData(NBTTagCompound tag)
	{
		target = tag.hasKey("EID") ? worldObj.getEntityByID(tag.getInteger("EID")) : null;
		updateScanArea();
	}
	
	public void updateScanArea()
	{
		updateBlockState();
		EnumFacing facing = currentState == null ? EnumFacing.UP : currentState.getValue(BlockTurret.FACING);
		
		int radius = 8;
		int maxX = pos.getX() + radius;
		int maxY = pos.getY() + radius;
		int maxZ = pos.getZ() + radius;
		int minX = pos.getX() - radius + 1;
		int minY = pos.getY() - radius + 1;
		int minZ = pos.getZ() - radius + 1;
		
		scanArea = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	private void searchForTarget()
	{
		updateScanArea();
		
		target = null;
		
		double x = pos.getX() + 0.5D;
		double y = pos.getY() + 0.5D;
		double z = pos.getZ() + 0.5D;
		double distSq = 0D;
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
	
	public void onUpdate()
	{
		if(getSide().isServer())
		{
			if(redstonePowered) return;
			
			if(cooldown > 0) cooldown--;
			
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
						playSound("silicio:turret_loop", 0.8F, 1F);
						cooldown = 20;
					}
				}
				else
				{
					searchForTarget();
					if(target == null) cooldown = 40;
				}
				
				if(hasTarget != (target != null))
				{
					if(target != null)
					{
						playSound("silicio:turret_start", 0.8F, 1F);
					}
					else
					{
						playSound("silicio:turret_end", 0.8F, 1F);
					}
					
					markDirty();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		if(target == null) return super.getRenderBoundingBox();
		else if(scanArea == null) updateScanArea();
		return scanArea;
	}
}
