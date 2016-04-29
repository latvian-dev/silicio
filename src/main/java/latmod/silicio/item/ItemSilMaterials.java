package latmod.silicio.item;

import ftb.lib.EnumMCColor;
import ftb.lib.LMMod;
import ftb.lib.api.item.ItemMaterialsLM;
import ftb.lib.api.item.MaterialItem;
import ftb.lib.api.item.ODItems;
import latmod.silicio.Silicio;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
	public static MaterialItem ANTIMATTER;
	
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
	
	public ItemSilMaterials()
	{
		super();
		setCreativeTab(Silicio.tab);
		setFolder("materials");
	}
	
	@Override
	public LMMod getMod()
	{ return Silicio.mod; }
	
	@Override
	public void onPostLoaded()
	{
		SILICON = add(new MaterialItem(this, 0, "silicon"));
		STONE_SHARD = add(new MaterialItem(this, 1, "stone_shard"));
		IRON_ROD = add(new MaterialItem(this, 2, "iron_rod"));
		BLUE_GOO = add(new MaterialItem(this, 3, "blue_goo"));
		LASER_LENS = add(new MaterialItem(this, 4, "laser_lens"));
		XSUIT_PLATE = add(new MaterialItem(this, 5, "xsuit_plate"));
		ANTIMATTER = add(new MaterialItem(this, 6, "antimatter"));
		
		ELEMITE_INGOT = add(new MaterialItem(this, 10, "elemite_ingot"));
		ELEMITE_NUGGET = add(new MaterialItem(this, 11, "elemite_nugget"));
		ELEMITE_DUST = add(new MaterialItem(this, 12, "elemite_dust"));
		
		WIRE = add(new MaterialItem(this, 20, "wire"));
		RESISTOR = add(new MaterialItem(this, 21, "resistor"));
		CAPACITOR = add(new MaterialItem(this, 22, "capacitor"));
		DIODE = add(new MaterialItem(this, 23, "diode"));
		TRANSISTOR = add(new MaterialItem(this, 24, "transistor"));
		CHIP = add(new MaterialItem(this, 25, "chip"));
		PROCESSOR = add(new MaterialItem(this, 26, "processor"));
		CIRCUIT = add(new MaterialItem(this, 27, "circuit"));
		CIRCUIT_WIFI = add(new MaterialItem(this, 28, "circuit_wifi"));
		LED_RED = add(new MaterialItem(this, 29, "led_red"));
		LED_GREEN = add(new MaterialItem(this, 30, "led_green"));
		LED_BLUE = add(new MaterialItem(this, 31, "led_blue"));
		LED_RGB = add(new MaterialItem(this, 32, "led_rgb"));
		LED_MATRIX = add(new MaterialItem(this, 33, "led_matrix"));
		
		MODULE_EMPTY = add(new MaterialItem(this, 60, "module_empty"));
		MODULE_INPUT = add(new MaterialItem(this, 61, "module_input"));
		MODULE_OUTPUT = add(new MaterialItem(this, 62, "module_output"));
		MODULE_LOGIC = add(new MaterialItem(this, 63, "module_logic"));
		
		ODItems.add(ODItems.IRON_ROD, IRON_ROD.getStack(1));
		ODItems.add(ODItems.SILICON, SILICON.getStack(1));
	}
	
	@Override
	public void loadRecipes()
	{
		getMod().recipes.addRecipe(IRON_ROD.getStack(4), "I", "I", 'I', ODItems.IRON);
		getMod().recipes.addShapelessRecipe(BLUE_GOO.getStack(1), ODItems.SLIMEBALL, ODItems.LAPIS, ODItems.IRON);
		getMod().recipes.addRecipe(XSUIT_PLATE.getStack(1), "EEE", "ESE", "EEE", 'E', ELEMITE_INGOT, 'S', ODItems.SILICON);
		
		getMod().recipes.addRecipe(ELEMITE_INGOT.getStack(1), "EEE", "EEE", "EEE", 'E', ELEMITE_NUGGET);
		getMod().recipes.addShapelessRecipe(ELEMITE_NUGGET.getStack(9), ELEMITE_INGOT);
		
		getMod().recipes.addSmelting(ELEMITE_INGOT.getStack(1), ELEMITE_DUST.getStack(1));
		getMod().recipes.addSmelting(ELEMITE_INGOT.getStack(1), BLUE_GOO.getStack(1));
		
		getMod().recipes.addRecipe(WIRE.getStack(8), "WWW", "NNN", "WWW", 'N', ELEMITE_NUGGET, 'W', new ItemStack(Blocks.carpet, 1, ODItems.ANY));
		getMod().recipes.addRecipe(RESISTOR.getStack(4), "WCW", 'C', Items.brick, 'W', WIRE);
		getMod().recipes.addRecipe(CAPACITOR.getStack(4), "WCW", 'C', Items.clay_ball, 'W', WIRE);
		getMod().recipes.addRecipe(DIODE.getStack(4), "WCW", 'C', ODItems.SILICON, 'W', WIRE);
		getMod().recipes.addRecipe(TRANSISTOR.getStack(3), "DDD", "WWW", 'D', DIODE, 'W', WIRE);
		getMod().recipes.addRecipe(CHIP.getStack(1), "TT", "TT", "TT", 'T', TRANSISTOR);
		getMod().recipes.addRecipe(PROCESSOR.getStack(1), "CCC", "CSC", "CCC", 'C', CHIP, 'S', ODItems.SILICON);
		getMod().recipes.addShapelessRecipe(CIRCUIT.getStack(1), PROCESSOR, WIRE, RESISTOR, CAPACITOR, TRANSISTOR, PROCESSOR, WIRE, WIRE);
		getMod().recipes.addRecipe(CIRCUIT_WIFI.getStack(1), "EEE", "ECE", "EEE", 'C', CIRCUIT, 'E', Items.ender_pearl);
		getMod().recipes.addShapelessRecipe(LED_RED.getStack(3), DIODE, EnumMCColor.RED.dyeName, ODItems.GLOWSTONE);
		getMod().recipes.addShapelessRecipe(LED_GREEN.getStack(3), DIODE, EnumMCColor.GREEN.dyeName, ODItems.GLOWSTONE);
		getMod().recipes.addShapelessRecipe(LED_BLUE.getStack(3), DIODE, EnumMCColor.BLUE.dyeName, ODItems.GLOWSTONE);
		getMod().recipes.addShapelessRecipe(LED_RGB.getStack(3), LED_RED, LED_GREEN, LED_BLUE);
		getMod().recipes.addRecipe(LED_MATRIX.getStack(1), "LLL", "LLL", "LLL", 'L', LED_RGB);
		
		getMod().recipes.addRecipe(MODULE_EMPTY.getStack(1), "III", "ICI", "III", 'I', ODItems.IRON, 'C', PROCESSOR.getStack(1));
		getMod().recipes.addShapelessRecipe(MODULE_INPUT.getStack(1), MODULE_EMPTY, EnumMCColor.BLUE.dyeName);
		getMod().recipes.addShapelessRecipe(MODULE_OUTPUT.getStack(1), MODULE_EMPTY, EnumMCColor.ORANGE.dyeName);
		getMod().recipes.addShapelessRecipe(MODULE_LOGIC.getStack(1), MODULE_EMPTY, ODItems.REDSTONE);
	}
}