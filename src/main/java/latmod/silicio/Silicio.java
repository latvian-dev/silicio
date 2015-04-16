package latmod.silicio;
import latmod.core.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:LatCoreMC")
public class Silicio
{
	protected static final String MOD_ID = "Silicio";
	
	@Mod.Instance(Silicio.MOD_ID)
	public static Silicio inst;
	
	@SidedProxy(clientSide = "latmod.silicio.client.SilClient", serverSide = "latmod.silicio.SilCommon")
	public static SilCommon proxy;
	
	public static LMMod mod;
	public static CreativeTabs tab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = new LMMod(e, new SilConfig(e), null);
		
		SilItems.init();
		mod.onPostLoaded();
		
		tab = mod.createTab("tab", new ItemStack(SilItems.b_cbcontroller));
		
		proxy.preInit(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		SilMat.init();
		
		if(!SilConfig.General.disableAllCrafting)
			mod.loadRecipes();
		
		if(LatCoreMC.isModInstalled("ThermalExpansion"))
		{
			try { LatCoreMC.invokeStatic("latmod.silicio.SilicioTE", "init"); }
			catch(Exception ex) { ex.printStackTrace(); };
		}
		
		/*
		if(LatCoreMC.isModInstalled("IC2"))
		{
			try { LatCoreMC.invokeStatic("latmod.silicio.integration.SilicioIC2", "init"); }
			catch(Exception ex) { ex.printStackTrace(); };
		}
		*/
		
		proxy.postInit(e);
	}
}