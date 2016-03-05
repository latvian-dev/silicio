package latmod.silicio.api.modules;

/**
 * Created by LatvianModder on 05.03.2016.
 */
public enum ConnectionType
{
	INPUT,
	OUTPUT,
	IO;
	
	public boolean isInput()
	{ return this == INPUT || this == IO; }
	
	public boolean isOutput()
	{ return this == OUTPUT || this == IO; }
}
