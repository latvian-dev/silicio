package latmod.silicio.tile;

import latmod.silicio.api.SignalChannel;
import latmod.silicio.api.tile.ISilNetController;

import java.util.Collection;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class TileConnector extends TileCBNetwork
{
    @Override
    public void provideSignals(ISilNetController c, Collection<SignalChannel> channels)
    {
        
    }
    
    @Override
    public void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on)
    {
        
    }
}