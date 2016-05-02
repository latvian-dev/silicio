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
		if(idx < 0 || idx >= 256) { throw new ArrayIndexOutOfBoundsException("Index can only be >= 0 & < 256"); }
		else if(t == null) { throw new NullPointerException("Type can't be null!"); }
		else if(id == null || id.isEmpty()) { throw new NullPointerException("ID can't be null!"); }
		
		index = idx;
		ID = id;
		type = t;
	}
	
	@Override
	public int hashCode()
	{ return index; }
	
	@Override
	public boolean equals(Object o)
	{ return o == this || hashCode() == o.hashCode(); }
	
	@Override
	public String getID()
	{ return ID; }
	
	@Override
	public int compareTo(ModuleIOConnection o)
	{ return getID().compareToIgnoreCase(o.getID()); }
}
