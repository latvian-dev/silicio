package latmod.silicio.api.modules;

import net.minecraft.item.ItemStack;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public interface IModuleItem
{
	Module getModule(ItemStack item);
}