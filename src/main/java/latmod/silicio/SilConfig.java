package latmod.silicio;
import latmod.ftbu.core.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
		public static boolean siliconOD;
		
		public static void load(Category c)
		{
			disableAllCrafting = c.getBool("disableAllCrafting", false);
			enableTERecipes = c.getBool("enableTERecipes", true);
			siliconOD = c.getBool("siliconOD", true);
			
			c.setComment("disableAllCrafting", "Set to true to disable all crafting recipes");
		}
	}
}