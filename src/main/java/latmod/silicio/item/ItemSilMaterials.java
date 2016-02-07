package latmod.silicio.item;

import ftb.lib.LMMod;
import ftb.lib.api.item.*;
import latmod.silicio.Silicio;

/**
 * Created by LatvianModder on 06.02.2016.
 */
public class ItemSilMaterials extends ItemMaterialsLM
{
	public static MaterialItem SILICON;
	public static MaterialItem STONE_SHARD;
	public static MaterialItem IRON_ROD;
	public static MaterialItem BLUE_GOO;
	public static MaterialItem LASER_LENS;
	public static MaterialItem XSUIT_PLATE;
	
	public static MaterialItem ELEMITE_INGOT;
	public static MaterialItem ELEMITE_NUGGET;
	public static MaterialItem ELEMITE_DUST;
	
	public static MaterialItem WIRE;
	public static MaterialItem RESISTOR;
	public static MaterialItem CAPACITOR;
	public static MaterialItem DIODE;
	public static MaterialItem TRANSISTOR;
	public static MaterialItem CHIP;
	public static MaterialItem PROCESSOR;
	public static MaterialItem CIRCUIT;
	public static MaterialItem CIRCUIT_WIFI;
	public static MaterialItem LED_RED;
	public static MaterialItem LED_GREEN;
	public static MaterialItem LED_BLUE;
	public static MaterialItem LED_RGB;
	public static MaterialItem LED_MATRIX;
	
	public static MaterialItem MODULE_EMPTY;
	public static MaterialItem MODULE_INPUT;
	public static MaterialItem MODULE_OUTPUT;
	public static MaterialItem MODULE_LOGIC;
	
	public ItemSilMaterials(String s)
	{
		super(s);
		setCreativeTab(Silicio.tab);
		setFolder("materials");
	}
	
	public LMMod getMod()
	{ return Silicio.mod; }
	
	public void onPostLoaded()
	{
		add(SILICON = new MaterialItem(this, 0, "silicon"));
		add(STONE_SHARD = new MaterialItem(this, 1, "stone_shard"));
		add(IRON_ROD = new MaterialItem(this, 2, "iron_rod"));
		add(BLUE_GOO = new MaterialItem(this, 3, "blue_goo"));
		add(LASER_LENS = new MaterialItem(this, 4, "laser_lens"));
		add(XSUIT_PLATE = new MaterialItem(this, 5, "xsuit_plate"));
		
		add(ELEMITE_INGOT = new MaterialItem(this, 10, "elemite_ingot"));
		add(ELEMITE_NUGGET = new MaterialItem(this, 11, "elemite_nugget"));
		add(ELEMITE_DUST = new MaterialItem(this, 12, "elemite_dust"));
		
		add(WIRE = new MaterialItem(this, 20, "wire"));
		add(RESISTOR = new MaterialItem(this, 21, "resistor"));
		add(CAPACITOR = new MaterialItem(this, 22, "capacitor"));
		add(DIODE = new MaterialItem(this, 23, "diode"));
		add(TRANSISTOR = new MaterialItem(this, 24, "transistor"));
		add(CHIP = new MaterialItem(this, 25, "chip"));
		add(PROCESSOR = new MaterialItem(this, 26, "processor"));
		add(CIRCUIT = new MaterialItem(this, 27, "circuit"));
		add(CIRCUIT_WIFI = new MaterialItem(this, 28, "circuit_wifi"));
		add(LED_RED = new MaterialItem(this, 29, "led_red"));
		add(LED_GREEN = new MaterialItem(this, 30, "led_green"));
		add(LED_BLUE = new MaterialItem(this, 31, "led_blue"));
		add(LED_RGB = new MaterialItem(this, 32, "led_rgb"));
		add(LED_MATRIX = new MaterialItem(this, 33, "led_matrix"));
		
		add(MODULE_EMPTY = new MaterialItem(this, 60, "module_empty"));
		add(MODULE_INPUT = new MaterialItem(this, 61, "module_input"));
		add(MODULE_OUTPUT = new MaterialItem(this, 62, "module_output"));
		add(MODULE_LOGIC = new MaterialItem(this, 63, "module_logic"));
		
		ODItems.add(ODItems.IRON_ROD, IRON_ROD.getStack());
	}
	
	public void loadRecipes()
	{
	}
}