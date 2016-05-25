package latmod.silicio.block;

import com.feed_the_beast.ftbl.util.BlockStateSerializer;
import com.feed_the_beast.ftbl.util.FTBLib;
import latmod.silicio.tile.TileLamp;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public class BlockLamp extends BlockSil
{
    public static final PropertyBool ON = PropertyBool.create("on");
    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class, EnumDyeColor.BLUE, EnumDyeColor.CYAN, EnumDyeColor.GREEN, EnumDyeColor.MAGENTA, EnumDyeColor.ORANGE, EnumDyeColor.PINK, EnumDyeColor.PURPLE, EnumDyeColor.RED, EnumDyeColor.YELLOW);

    public BlockLamp()
    {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(COLOR, EnumDyeColor.BLUE));
    }

    @Override
    public void loadTiles()
    {
        FTBLib.addTile(TileLamp.class, getRegistryName());
    }

    @Override
    public void loadModels()
    {
        for(EnumDyeColor color : COLOR.getAllowedValues())
        {
            ModelLoader.setCustomModelResourceLocation(getItem(), color.getMetadata(), new ModelResourceLocation(getRegistryName(), BlockStateSerializer.getString(blockState.getBaseState().withProperty(ON, true).withProperty(COLOR, color))));
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World w, IBlockState state)
    {
        return new TileLamp();
    }

    @Override
    public void loadRecipes()
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumDyeColor color : COLOR.getAllowedValues())
        {
            list.add(new ItemStack(itemIn, 1, color.getMetadata()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer ep, List<String> l, boolean b)
    {
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR, ON);
    }

    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        boolean enabled = false;

        if(te instanceof TileLamp)
        {
            enabled = ((TileLamp) te).enabled;
        }

        return state.withProperty(ON, enabled);
    }
}