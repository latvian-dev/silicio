package latmod.silicio.tile;

import latmod.core.EnumDyeColor;
import latmod.core.util.*;
import net.minecraft.nbt.NBTTagCompound;

public final class CBChannel
{
	public static final CBChannel NONE = new CBChannel(0);
	
	public static enum Type
	{
		NONE,
		GLOBAL,
		LOCAL;
		
		Type() {}
		
		public boolean canModify()
		{ return this != NONE; }
		
		public static Type getFromID(int id)
		{
			if(id == 0) return NONE;
			return (id > 0) ? GLOBAL : LOCAL;
		}
	}
	
	public final int ID;
	public final Type type;
	
	private boolean isEnabled;
	
	private CBChannel(int id)
	{
		ID = id;
		type = Type.getFromID(id);
	}
	
	public void enable()
	{ if(type.canModify()) isEnabled = true; }
	
	public boolean isEnabled()
	{ return type.canModify() ? isEnabled : false; }
	
	public int hashCode()
	{ return ID; }
	
	public boolean equals(Object o)
	{ return o != null && (o == this || o.hashCode() == hashCode()); }
	
	public CBChannel copy()
	{
		CBChannel c = new CBChannel(ID);
		c.isEnabled = isEnabled();
		return c;
	}
	
	public String toString()
	{ return channelToString(ID); }
	
	public static String channelToString(int id)
	{
		if(id == 0) return "IO Disabled";
		String s = (id < 0) ? "Local " : "Global ";
		s += CBChannel.getColor(id).toString();
		if(id > 0) s += " #" + (CBChannel.getWarppedID(id) / 16 + 1);
		return s;
	}
	
	public static int getWarppedID(int id)
	{
		if(id == 0) return 0;
		return Math.abs(id) - 1;
	}
	
	public static EnumDyeColor getColor(int id)
	{ return EnumDyeColor.VALUES[getWarppedID(id) % 16]; }
	
	public static CBChannel[] create(int i, Type t)
	{
		CBChannel[] c = new CBChannel[i];
		for(int j = 0; j < c.length; j++)
		c[j] = new CBChannel((t == Type.LOCAL) ? -j : j);
		return c;
	}
	
	public FastList<CBChannel> getAllEnabled(CBChannel[] channels)
	{
		FastList<CBChannel> al = new FastList<CBChannel>();
		
		for(int i = 0; i < channels.length; i++)
			if(channels[i].isEnabled) al.add(channels[i]);
		
		return al;
	}
	
	public static void clear(CBChannel[] channels)
	{
		for(int i = 0; i  < channels.length; i++)
			channels[i].isEnabled = false;
	}
	
	public static void readFromNBT(NBTTagCompound tag, String s, CBChannel[] channels)
	{
		clear(channels); int[] en = tag.getIntArray(s);
		if(en.length > 0) for(int i : en) channels[i].isEnabled = true;
	}
	
	public static void writeToNBT(NBTTagCompound tag, String s, CBChannel[] channels)
	{
		FastList<Integer> en = new FastList<Integer>();
		for(int i = 0; i < channels.length; i++)
		if(channels[i].isEnabled) en.add(i);
		if(!en.isEmpty()) tag.setIntArray(s, Converter.toInts(en.toArray(new Integer[0])));
	}
	
	public static void copy(CBChannel[] from, CBChannel[] to)
	{
		if(from == null || to == null) return;
		if(from.length != to.length) return;
		for(int i = 0; i < from.length; i++)
			to[i] = from[i].copy();
	}
}