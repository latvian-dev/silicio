package latmod.silicio.api.modules;

import latmod.lib.util.FinalIDObject;

/**
 * Created by LatvianModder on 04.03.2016.
 */
public final class ModuleChannel extends FinalIDObject
{
	public enum Type
	{
		INPUT,
		OUTPUT,
		IO;
		
		public boolean isInput()
		{ return this == INPUT || this == IO; }
		
		public boolean isOutput()
		{ return this == OUTPUT || this == IO; }
	}
	
	public final int ordinal;
	public final Type type;
	
	public ModuleChannel(int index, String id, Type t)
	{
		super(id);
		ordinal = index;
		type = t;
	}
}
