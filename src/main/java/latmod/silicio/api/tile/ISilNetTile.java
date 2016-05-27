package latmod.silicio.api.tile;

import latmod.silicio.api.SignalChannel;

import java.util.Collection;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ISilNetTile
{
    void provideSignals(ISilNetController c, Collection<SignalChannel> channels);

    void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on);
}