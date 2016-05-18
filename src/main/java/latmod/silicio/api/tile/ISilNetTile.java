package latmod.silicio.api.tile;

import com.feed_the_beast.ftbl.api.tile.ITileEntity;
import latmod.silicio.api.SignalChannel;

import java.util.Collection;

/**
 * Created by LatvianModder on 03.03.2016.
 */
public interface ISilNetTile extends ITileEntity
{
	void provideSignals(ISilNetController c, Collection<SignalChannel> channels);
	void onSignalChanged(ISilNetController c, SignalChannel channel, boolean on);
}