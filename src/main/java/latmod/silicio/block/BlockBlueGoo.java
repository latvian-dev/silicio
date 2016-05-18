package latmod.silicio.block;

import latmod.silicio.item.SilItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBlueGoo extends BlockSil
{
    public BlockBlueGoo()
    {
        super(Material.CLAY);
        slipperiness = 0.8F;
        setSoundType(SoundType.SLIME);
    }
    
    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(new ItemStack(this), "GGG", "GGG", "GGG", 'G', SilItems.BLUE_GOO);
        getMod().recipes.addShapelessRecipe(SilItems.BLUE_GOO.getStack(9), this);
    }
    
    @Override
    public MapColor getMapColor(IBlockState state)
    { return MapColor.LIGHT_BLUE; }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    { return BlockRenderLayer.TRANSLUCENT; }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    { return false; }
    
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
        if(entityIn.isSneaking())
        {
            super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
        }
        else
        {
            entityIn.fall(fallDistance, 0F);
        }
    }
    
    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        if(entityIn.isSneaking())
        {
            super.onLanded(worldIn, entityIn);
        }
        else if(entityIn.motionY < 0D)
        {
            entityIn.motionY = -entityIn.motionY;
        }
    }
    
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if(Math.abs(entityIn.motionY) < 0.1D && !entityIn.isSneaking())
        {
            double d0 = 0.4D + Math.abs(entityIn.motionY) * 0.2D;
            entityIn.motionX *= d0;
            entityIn.motionZ *= d0;
        }
        
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}