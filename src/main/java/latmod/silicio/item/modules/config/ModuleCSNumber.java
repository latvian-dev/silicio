package latmod.silicio.item.modules.config;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.*;

public class ModuleCSNumber extends ModuleConfigSegment<Number>
{
	public static enum Type
	{
		BYTE,
		SHORT,
		INT,
		LONG,
		FLOAT,
		DOUBLE
	}
	
	public final Type type;
	
	public ModuleCSNumber(Type t)
	{ type = t; }
	
	@SideOnly(Side.CLIENT)
	public void openGui(Minecraft mc)
	{
	}
	
	public void setData(NBTTagCompound data, int ID, Number n)
	{
		String s = "" + ID;
		if(type == Type.BYTE) data.setByte(s, n.byteValue());
		if(type == Type.SHORT) data.setShort(s, n.shortValue());
		if(type == Type.INT) data.setInteger(s, n.intValue());
		if(type == Type.LONG) data.setLong(s, n.longValue());
		if(type == Type.FLOAT) data.setFloat(s, n.floatValue());
		if(type == Type.DOUBLE) data.setDouble(s, n.doubleValue());
	}
	
	public Number getData(NBTTagCompound data, int ID)
	{
		Number n = 0;
		
		String s = "" + ID;
		if(type == Type.BYTE) n = data.getByte(s);
		if(type == Type.SHORT) n = data.getShort(s);
		if(type == Type.INT) n = data.getInteger(s);
		if(type == Type.LONG) n = data.getLong(s);
		if(type == Type.FLOAT) n = data.getFloat(s);
		if(type == Type.DOUBLE) n = data.getDouble(s);
		
		return n;
	}
}