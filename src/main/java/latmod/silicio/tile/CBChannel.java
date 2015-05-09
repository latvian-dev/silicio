package latmod.silicio.tile;

import latmod.core.EnumDyeColor;
import latmod.core.util.*;
import net.minecraft.nbt.NBTTagCompound;

public final class CBChannel
{
	public static final CBChannel NONE = new CBChannel(-1, "IO Disabled");
	
	public final int ID;
	public final EnumDyeColor color;
	private boolean isEnabled;
	public String name;
	
	public CBChannel(int id, String s)
	{
		ID = id;
		color = (ID >= 0) ? EnumDyeColor.VALUES[ID % 16] : EnumDyeColor.BLACK;
		name = (s != null && !s.isEmpty()) ? ("" + s) : getChannelName(ID);
	}
	
	public boolean isNone()
	{ return this == NONE || ID < 0; }
	
	public static String getChannelName(int i) { if(i < 0) return NONE.name;
	return (EnumDyeColor.VALUES[i % 16].toString() + " #" + (i / 16 + 1)); }
	
	public void enable()
	{ if(!isNone()) isEnabled = true; }
	
	public boolean isEnabled()
	{ return !isNone() && isEnabled; }
	
	public int hashCode()
	{ return ID; }
	
	public boolean equals(Object o)
	{ return o != null && (o == this || o.hashCode() == hashCode()); }
	
	public CBChannel copy()
	{
		CBChannel c = new CBChannel(ID, name);
		c.isEnabled = isEnabled();
		return c;
	}
	
	public String toString()
	{ return name; }
	
	public FastList<CBChannel> getAllEnabled(CBChannel[] channels)
	{
		FastList<CBChannel> al = new FastList<CBChannel>();
		
		for(int i = 0; i < channels.length; i++)
			if(channels[i].isEnabled) al.add(channels[i]);
		
		return al;
	}
	
	public static void readFromNBT(NBTTagCompound tag, String s, CBChannel[] channels)
	{
		clear(channels); int[] en = tag.getIntArray(s);
		if(en.length > 0) for(int i : en) channels[i].isEnabled = true;
	}
	
	public static void writeToNBT(NBTTagCompound tag, String s, CBChannel[] channels)
	{
		IntList en = new IntList();
		for(int i = 0; i < channels.length; i++)
		if(channels[i].isEnabled) en.add(i);
		if(!en.isEmpty()) tag.setIntArray(s, en.array);
	}
	
	public static void clear(CBChannel[] channels)
	{
		for(int i = 0; i  < channels.length; i++)
			channels[i].isEnabled = false;
	}
	
	public static CBChannel[] create(int l)
	{
		CBChannel[] c = new CBChannel[l];
		for(int i = 0; i < l; i++) c[i] = new CBChannel(i, null);
		return c;
	}
	
	public static void copy(CBChannel[] from, CBChannel[] to)
	{
		int l1 = Math.min(from.length, to.length);
		for(int i = 0; i < l1; i++) to[i] = from[i].copy();
	}
}