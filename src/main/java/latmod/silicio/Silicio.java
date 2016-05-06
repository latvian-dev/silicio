package latmod.silicio;

import ftb.lib.CreativeTabLM;
import ftb.lib.LMMod;
import latmod.silicio.api.tile.energy.CapabilitySilEnergyTank;
import latmod.silicio.block.BlockSilMachines;
import latmod.silicio.block.SilBlocks;
import latmod.silicio.item.SilItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Silicio.MOD_ID, name = "Silicio", version = "@VERSION@", dependencies = "required-after:ftbl;after:ComputerCraft")
//;required-after:mcmultipart
public class Silicio
{
	protected static final String MOD_ID = "silicio";
	
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
		SilBlocks.init();
		SilSounds.init();
		
		tab.addIcon(BlockSilMachines.EnumVariant.CONTROLLER.getStack(1));
		
		mod.onPostLoaded();
		
		CapabilitySilEnergyTank.enable();
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		mod.loadRecipes();
	}
}