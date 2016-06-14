package latmod.silicio.tile;

import com.feed_the_beast.ftbl.api.tile.TileLM;
import latmod.silicio.api.SignalChannel;
import latmod.silicio.api.SilCapabilities;
import latmod.silicio.api.tile.ISilNetController;
import latmod.silicio.api.tile.ISilNetTile;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public class TileSilNet extends TileLM implements ISilNetTile
{
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nonnull EnumFacing facing)
    {
        if(capability == SilCapabilities.SILNET_TILE)
        {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Nonnull
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nonnull EnumFacing facing)
    {
        if(capability == SilCapabilities.SILNET_TILE)
        {
            return (T) this;
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void provideSignals(ISilNetController c, Collection<SignalChannel> channels)
    {
    }

    @Override
    public void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on)
    {
    }
}