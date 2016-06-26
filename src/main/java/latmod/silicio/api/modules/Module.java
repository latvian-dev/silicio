package latmod.silicio.api.modules;

import com.latmod.lib.io.Bits;
import latmod.silicio.api.SignalChannel;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public abstract class Module
{
    public static final byte FLAG_PROVIDE_SIGNALS = 0;

    private byte flags = 0;

    protected void setFlag(byte flag, boolean b)
    {
        flags = Bits.setBit(flags, flag, b);
    }

    public boolean getFlag(byte flag)
    {
        return Bits.getBit(flags, flag);
    }

    public void init(ModuleContainer c)
    {
    }

    public void onAdded(ModuleContainer c, EntityPlayerMP player)
    {
    }

    public void onRemoved(ModuleContainer c, EntityPlayerMP player)
    {
    }

    public void onUpdate(ModuleContainer c)
    {
    }

    public void provideSignals(ModuleContainer c, Collection<SignalChannel> list)
    {
    }

    public void onSignalChanged(ModuleContainer c, SignalChannel id, boolean on)
    {
    }
}