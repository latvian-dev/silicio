package latmod.silicio.api.tile;

import java.util.List;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public interface ICBController extends ICBNetTile
{
	boolean hasConflict();
	boolean getSignalState(int id);
	List<ICBNetTile> getNetwork();
}
