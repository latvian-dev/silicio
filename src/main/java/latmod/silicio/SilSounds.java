package latmod.silicio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by LatvianModder on 29.04.2016.
 */
public class SilSounds
{
    private static SoundEvent register(String name)
    {
        ResourceLocation rl = new ResourceLocation("silicio", name);
        SoundEvent event = new SoundEvent(rl);
        event.setRegistryName(rl);
        return GameRegistry.register(event);
    }
    
    public static final SoundEvent TURRET_START = register("turret_start");
    public static final SoundEvent TURRET_LOOP = register("turret_loop");
    public static final SoundEvent TURRET_END = register("turret_end");
    
    public static void init()
    {
    }
}