package com.latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.EnumSync;
import com.latmod.silicio.api.modules.ModuleContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class TileSocketBlock extends TileSilNet
{
    public final Map<EnumFacing, ModuleContainer> modules;

    public TileSocketBlock()
    {
        modules = new EnumMap<>(EnumFacing.class);
    }

    @Override
    public EnumSync getSync()
    {
        return EnumSync.RERENDER;
    }

    @Override
    public void readTileData(@Nonnull NBTTagCompound tag)
    {
        super.readTileData(tag);

        modules.clear();

        NBTTagList list = tag.getTagList("Modules", Constants.NBT.TAG_COMPOUND);

        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound tag1 = list.getCompoundTagAt(i);
            ModuleContainer c = ModuleContainer.readFromNBT(this, tag1);
            if(c != null)
            {
                modules.put(c.facing, c);
                c.module.init(c);
            }
        }
    }

    @Override
    public void writeTileData(@Nonnull NBTTagCompound tag)
    {
        super.writeTileData(tag);

        NBTTagList list = new NBTTagList();

        for(ModuleContainer m : modules.values())
        {
            NBTTagCompound tag1 = new NBTTagCompound();
            m.writeToNBT(tag1);
            list.appendTag(tag1);
        }

        tag.setTag("Modules", list);
    }
}