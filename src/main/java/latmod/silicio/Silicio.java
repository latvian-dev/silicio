package latmod.silicio;
import latmod.core.LMMod;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:LatCoreMC;required-after:ThermalExpansion")
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
		mod = new LMMod(e, new SilConfig(e), new SilRecipes());
		
		SilItems.init();
		mod.onPostLoaded();
		
		tab = mod.createTab("tab", SilMat.SILICON);
		
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
		
		proxy.postInit(e);
	}
}