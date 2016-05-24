package latmod.silicio.item;

import com.feed_the_beast.ftbl.api.item.ItemMaterialsLM;
import com.feed_the_beast.ftbl.api.item.ODItems;
import com.feed_the_beast.ftbl.util.EnumDyeColorHelper;
import com.feed_the_beast.ftbl.util.LMMod;
import latmod.silicio.Silicio;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 06.02.2016.
 */
public class ItemSilMaterials extends ItemMaterialsLM
{
    public ItemSilMaterials()
    {
        setCreativeTab(Silicio.tab);
    }

    @Override
    public LMMod getMod()
    {
        return Silicio.mod;
    }

    @Override
    public String getFolder()
    {
        return "materials";
    }

    @Override
    public void onPostLoaded()
    {
        addAll(SilItems.class);

        ODItems.add(ODItems.IRON_ROD, SilItems.IRON_ROD.getStack(1));
        ODItems.add(ODItems.SILICON, SilItems.SILICON.getStack(1));

        ODItems.add(SilItems.ORE_ELEMITE_DUST, SilItems.ELEMITE_DUST.getStack(1));
        ODItems.add(SilItems.ORE_ELEMITE_INGOT, SilItems.ELEMITE_INGOT.getStack(1));
        ODItems.add(SilItems.ORE_ELEMITE_NUGGET, SilItems.ELEMITE_NUGGET.getStack(1));
    }

    @Override
    public void loadRecipes()
    {
        getMod().recipes.addRecipe(SilItems.IRON_ROD.getStack(4), "I", "I", 'I', ODItems.IRON);
        getMod().recipes.addShapelessRecipe(SilItems.BLUE_GOO.getStack(1), ODItems.SLIMEBALL, ODItems.LAPIS, ODItems.IRON);
        getMod().recipes.addRecipe(SilItems.XSUIT_PLATE.getStack(1), "EEE", "ESE", "EEE", 'E', SilItems.ORE_ELEMITE_INGOT, 'S', ODItems.SILICON);

        getMod().recipes.addRecipe(SilItems.ELEMITE_INGOT.getStack(1), "EEE", "EEE", "EEE", 'E', SilItems.ORE_ELEMITE_NUGGET);
        getMod().recipes.addShapelessRecipe(SilItems.ELEMITE_NUGGET.getStack(9), SilItems.ORE_ELEMITE_INGOT);

        getMod().recipes.addSmelting(SilItems.ELEMITE_INGOT.getStack(1), SilItems.ELEMITE_DUST.getStack(1));
        getMod().recipes.addSmelting(SilItems.ELEMITE_INGOT.getStack(1), SilItems.BLUE_GOO.getStack(1));

        getMod().recipes.addRecipe(SilItems.WIRE.getStack(8), "WWW", "NNN", "WWW", 'N', SilItems.ORE_ELEMITE_NUGGET, 'W', new ItemStack(Blocks.CARPET, 1, ODItems.ANY));
        getMod().recipes.addRecipe(SilItems.RESISTOR.getStack(4), "WCW", 'C', Items.BRICK, 'W', SilItems.WIRE);
        getMod().recipes.addRecipe(SilItems.CAPACITOR.getStack(4), "WCW", 'C', Items.CLAY_BALL, 'W', SilItems.WIRE);
        getMod().recipes.addRecipe(SilItems.DIODE.getStack(4), "WCW", 'C', ODItems.SILICON, 'W', SilItems.WIRE);
        getMod().recipes.addRecipe(SilItems.TRANSISTOR.getStack(3), "DDD", "WWW", 'D', SilItems.DIODE, 'W', SilItems.WIRE);
        getMod().recipes.addRecipe(SilItems.CHIP.getStack(1), "TT", "TT", "TT", 'T', SilItems.TRANSISTOR);
        getMod().recipes.addRecipe(SilItems.PROCESSOR.getStack(1), "CCC", "CSC", "CCC", 'C', SilItems.CHIP, 'S', ODItems.SILICON);
        getMod().recipes.addShapelessRecipe(SilItems.CIRCUIT.getStack(1), SilItems.PROCESSOR, SilItems.WIRE, SilItems.RESISTOR, SilItems.CAPACITOR, SilItems.TRANSISTOR, SilItems.PROCESSOR, SilItems.WIRE, SilItems.WIRE, SilItems.ORE_ELEMITE_NUGGET);
        getMod().recipes.addRecipe(SilItems.CIRCUIT_WIFI.getStack(1), "NEN", "ECE", "NEN", 'C', SilItems.CIRCUIT, 'E', Items.ENDER_PEARL, 'N', SilItems.ORE_ELEMITE_NUGGET);
        getMod().recipes.addShapelessRecipe(SilItems.LED_RED.getStack(3), SilItems.DIODE, EnumDyeColorHelper.get(EnumDyeColor.RED).dyeName, ODItems.GLOWSTONE);
        getMod().recipes.addShapelessRecipe(SilItems.LED_GREEN.getStack(3), SilItems.DIODE, EnumDyeColorHelper.get(EnumDyeColor.GREEN).dyeName, ODItems.GLOWSTONE);
        getMod().recipes.addShapelessRecipe(SilItems.LED_BLUE.getStack(3), SilItems.DIODE, EnumDyeColorHelper.get(EnumDyeColor.BLUE).dyeName, ODItems.GLOWSTONE);
        getMod().recipes.addShapelessRecipe(SilItems.LED_RGB.getStack(3), SilItems.LED_RED, SilItems.LED_GREEN, SilItems.LED_BLUE);
        getMod().recipes.addRecipe(SilItems.LED_MATRIX.getStack(1), "LLL", "LLL", "LLL", 'L', SilItems.LED_RGB);

        getMod().recipes.addRecipe(SilItems.MODULE_EMPTY.getStack(1), "III", "ICI", "III", 'I', ODItems.IRON, 'C', SilItems.PROCESSOR);
        getMod().recipes.addShapelessRecipe(SilItems.MODULE_INPUT.getStack(1), SilItems.MODULE_EMPTY, EnumDyeColorHelper.get(EnumDyeColor.LIGHT_BLUE).dyeName);
        getMod().recipes.addShapelessRecipe(SilItems.MODULE_OUTPUT.getStack(1), SilItems.MODULE_EMPTY, EnumDyeColorHelper.get(EnumDyeColor.ORANGE).dyeName);
        getMod().recipes.addShapelessRecipe(SilItems.MODULE_LOGIC.getStack(1), SilItems.MODULE_EMPTY, ODItems.REDSTONE);
    }
}