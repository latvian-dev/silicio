package latmod.silicio.item.modules;

public enum IOType
{
	INPUT,
	OUTPUT,
	NONE,
	BOTH;
	
	IOType() { }
	
	public boolean isNone()
	{ return this == NONE; }
	
	public boolean isInput()
	{ return this == INPUT || this == BOTH; }
	
	public boolean isOutput()
	{ return this == OUTPUT || this == BOTH; }
}