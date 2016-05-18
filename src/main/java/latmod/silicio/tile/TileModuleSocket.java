package latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.item.LMInvUtils;
import latmod.silicio.api.SilCapabilities;
import latmod.silicio.api.modules.Module;
import latmod.silicio.api.modules.ModuleContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.util.Constants;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class TileModuleSocket extends TileCBNetwork
{
    public final Map<EnumFacing, ModuleContainer> modules;
    
    public TileModuleSocket()
    {
        modules = new EnumMap<>(EnumFacing.class);
    }
    
    @Override
    public EnumSync getSync()
    { return EnumSync.RERENDER; }
    
    @Override
    public void readTileData(NBTTagCompound tag)
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
    public void writeTileData(NBTTagCompound tag)
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
    
    @Override
    public boolean onRightClick(EntityPlayer ep, ItemStack is, EnumFacing side, EnumHand hand, float x, float y, float z)
    {
        ModuleContainer c = modules.get(side);
        
        if(c != null)
        {
            if(getSide().isServer())
            {
                if(is == null && ep.isSneaking())
                {
                    c.module.onRemoved(c, (EntityPlayerMP) ep);
                    LMInvUtils.giveItem(ep, c.item.copy(), ep.inventory.currentItem);
                    modules.remove(side);
                    markDirty();
                }
            }
            
            return true;
        }
        else if(is != null && is.hasCapability(SilCapabilities.MODULE, null))
        {
            if(getSide().isServer())
            {
                Module m = is.getCapability(SilCapabilities.MODULE, null);
                
                if(m != null)
                {
                    c = new ModuleContainer(this, side, LMInvUtils.singleCopy(is), m);
                    modules.put(c.facing, c);
                    is.stackSize--;
                    c.module.init(c);
                    c.module.onAdded(c, (EntityPlayerMP) ep);
                    markDirty();
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public void onUpdate()
    {
    }
}