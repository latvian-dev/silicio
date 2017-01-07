package com.latmod.silicio.api.pipes;

import com.feed_the_beast.ftbl.lib.util.LMNetUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * Created by LatvianModder on 04.01.2017.
 */
public final class TransportedItem implements INBTSerializable<NBTTagCompound>
{
    public static final float MIN_SPEED = 0.1F / 20F;
    public static final float MAX_SPEED = 18F / 20F;
    private static final float[][] POS_MULTIPLIER = new float[6][3];

    static
    {
        for(int i = 0; i < 6; i++)
        {
            Vec3i vec = EnumFacing.VALUES[i].getDirectionVec();
            POS_MULTIPLIER[i][0] = vec.getX() * 0.5F;
            POS_MULTIPLIER[i][1] = vec.getY() * 0.5F;
            POS_MULTIPLIER[i][2] = vec.getZ() * 0.5F;
        }
    }

    public ItemStack stack;
    public float speed = MIN_SPEED, progress = 0F, pprogress = 0F;
    public byte src = -1, dst = -1;
    public short filters = 0;

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Item", stack.serializeNBT());
        nbt.setFloat("Speed", speed);
        nbt.setFloat("Progress", progress);
        nbt.setByte("Directions", (byte) (src | (dst << 3)));

        if(filters != 0)
        {
            nbt.setShort("Filters", filters);
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
        speed = nbt.getFloat("Speed");
        progress = pprogress = nbt.getFloat("Progress");
        byte dirs = nbt.getByte("Directions");
        src = (byte) (dirs & 7);
        dst = (byte) ((dirs >> 3) & 7);
        filters = nbt.getShort("Filters");
    }

    public void toBytes(ByteBuf io)
    {
        LMNetUtils.writeItemStack(io, stack);
        io.writeFloat(speed);
        io.writeFloat(progress);
        io.writeByte(src | (dst << 3));
        io.writeShort(filters);
    }

    public void fromBytes(ByteBuf io)
    {
        stack = LMNetUtils.readItemStack(io);
        speed = io.readFloat();
        progress = pprogress = io.readFloat();
        byte dirs = io.readByte();
        src = (byte) (dirs & 7);
        dst = (byte) ((dirs >> 3) & 7);
        filters = io.readShort();
    }

    public void setSpeed(float s)
    {
        if(s < MIN_SPEED)
        {
            speed = MIN_SPEED;
        }
        else if(s > MAX_SPEED)
        {
            speed = MAX_SPEED;
        }
        else
        {
            speed = s;
        }
    }

    public void getPos(float[] pos, float partialTicks)
    {
        float[] multiplier = POS_MULTIPLIER[progress <= 0.5F ? src : dst];
        float p;

        float progress1 = pprogress + (progress - pprogress) * partialTicks;

        if(progress1 <= 0.5F)
        {
            p = 1F - progress1 * 2F;
        }
        else
        {
            p = 1F - (1F - progress1) * 2F;
        }

        pos[0] = multiplier[0] * p;
        pos[1] = multiplier[1] * p;
        pos[2] = multiplier[2] * p;
    }
}