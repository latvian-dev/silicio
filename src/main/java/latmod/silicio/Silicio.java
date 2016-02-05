package latmod.silicio;

import ftb.lib.LMMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:FTBU;required-after:LatBlocks;after:ThermalExpansion;after:ComputerCraft;after:BuildCraft")
public class Silicio
{
	protected static final String MOD_ID = "Silicio";
	
	@Mod.Instance(MOD_ID)
	public static Silicio inst;
	
	@SidedProxy(clientSide = "latmod.silicio.client.SilClient", serverSide = "latmod.silicio.SilCommon")
	public static SilCommon proxy;
	
	public static LMMod mod;
	public static CreativeTabs tab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = LMMod.create(Silicio.MOD_ID);
		//SilConfig.load();
		SilItems.init();
		mod.onPostLoaded();
		
		tab = mod.createTab("tab", new ItemStack(Items.apple));//new ItemStack(SilItems.b_cbcontroller));
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		//if(!SilConfigGeneral.disableAllCrafting.get()) mod.loadRecipes();
	}
}