package latmod.silicio.api.tile.cb;

import java.util.Collection;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ICBController extends ICBNetTile
{
	boolean getSignalState(int id);
	Collection<ICBNetTile> getNetwork();
	void refreshNetwork();
}
