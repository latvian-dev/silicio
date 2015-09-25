package latmod.silicio;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import latmod.ftbu.util.LMMod;
import latmod.silicio.integration.SilInt;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:FTBU;required-after:LatBlocks;after:ThermalExpansion;after:ComputerCraft;after:BuildCraft")
public class Silicio
{
	protected static final String MOD_ID = "Silicio";
	
	@Mod.Instance(MOD_ID)
	public static Silicio inst;
	
	@SidedProxy(clientSide = "latmod.silicio.client.SilClient", serverSide = "latmod.silicio.SilCommon")
	public static SilCommon proxy;
	
	@LMMod.Instance(MOD_ID)
	public static LMMod mod;
	public static CreativeTabs tab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		LMMod.init(this, new SilConfig(e), null);
		
		SilItems.init();
		mod.onPostLoaded();
		
		tab = mod.createTab("tab", new ItemStack(SilItems.b_cbcontroller));
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		if(!SilConfig.General.disableAllCrafting)
			mod.loadRecipes();
		
		SilInt.onLoadedAll();
	}
}