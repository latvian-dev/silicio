package latmod.silicio;

import ftb.lib.*;
import latmod.silicio.multiparts.SilicioMultipartRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:FTBL;after:ComputerCraft;required-after:mcmultipart")
public class Silicio
{
	protected static final String MOD_ID = "Silicio";
	
	@Mod.Instance(MOD_ID)
	public static Silicio inst;
	
	@SidedProxy(clientSide = "latmod.silicio.client.SilClient", serverSide = "latmod.silicio.SilCommon")
	public static SilCommon proxy;
	
	public static LMMod mod;
	public static CreativeTabLM tab;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		mod = LMMod.create(Silicio.MOD_ID);
		tab = new CreativeTabLM("silicio").setMod(mod);
		//SilConfig.load();
		SilItems.init();
		
		tab.addIcon(new ItemStack(SilItems.b_controller));
		
		mod.onPostLoaded();
		proxy.preInit();
		
		SilicioMultipartRegistry.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
	}
}