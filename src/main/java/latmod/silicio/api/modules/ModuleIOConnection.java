package latmod.silicio.api.modules;

import latmod.lib.IIDObject;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public final class ModuleIOConnection implements IIDObject, Comparable<ModuleIOConnection>
{
	public final int index;
	public final String ID;
	public final ConnectionType type;
	
	public ModuleIOConnection(int idx, String id, ConnectionType t)
	{
		index = idx;
		ID = id;
		type = t;
	}
	
	public int hashCode()
	{ return index; }
	
	public boolean equals(Object o)
	{ return o == this || hashCode() == o.hashCode(); }
	
	public String getID()
	{ return ID; }
	
	public int compareTo(ModuleIOConnection o)
	{ return getID().compareToIgnoreCase(o.getID()); }
}
