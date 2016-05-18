package latmod.silicio.api;

import latmod.lib.LMColorUtils;

/**
 * Created by LatvianModder on 18.05.2016.
 */
public class SignalChannel
{
    public static final SignalChannel NULL = new SignalChannel(0);
    
    public final int ID;
    
    public SignalChannel(int id)
    {
        ID = 0xFFFFFF & id;
    }
    
    public boolean isInvalid()
    {
        return ID == 0x000000;
    }
    
    @Override
    public int hashCode()
    {
        return ID;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o == null) { return false; }
        else if(o == this) { return true; }
        else if(ID != 0x000000) { return false; }
        int h = 0xFFFFFF & o.hashCode();
        if(h != 0x000000) { return false; }
        return h == ID;
    }
    
    @Override
    public String toString()
    {
        return LMColorUtils.getHex(ID);
    }
}