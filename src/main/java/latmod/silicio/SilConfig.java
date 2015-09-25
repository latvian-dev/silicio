package latmod.silicio;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import latmod.ftbu.api.IServerConfig;
import latmod.ftbu.util.LMConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class SilConfig extends LMConfig implements IServerConfig
{
	public SilConfig(FMLPreInitializationEvent e)
	{ super(e, "/LatMod/Silicio.cfg"); }
	
	public void load()
	{
		General.load(get("general"));
	}
	
	public void readConfig(NBTTagCompound tag)
	{
		//boolean[] b = readBools(tag, "C");
		//General.bool = b[0];
	}
	
	public void writeConfig(NBTTagCompound tag, EntityPlayerMP ep)
	{
		//writeBools(tag, "C",
		//General.bool);
	}
	
	public static class General
	{
		public static boolean disableAllCrafting;
		public static boolean enableTERecipes;
		public static boolean siliconRecipe;
		
		public static void load(Category c)
		{
			disableAllCrafting = c.getBool("disableAllCrafting", false);
			enableTERecipes = c.getBool("enableTERecipes", true);
			siliconRecipe = c.getBool("siliconRecipe", true);
		}
	}
}