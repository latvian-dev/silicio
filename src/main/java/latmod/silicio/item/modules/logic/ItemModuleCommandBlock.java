package latmod.silicio.item.modules.logic;

import latmod.silicio.item.modules.*;
import latmod.silicio.item.modules.config.ModuleCSString;
import latmod.silicio.tile.cb.CircuitBoard;
import latmod.silicio.tile.cb.events.EventChannelToggled;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemModuleCommandBlock extends ItemModuleLogic implements IToggable
{
	public static final ModuleCSString cs_command = new ModuleCSString(0, "Command");
	
	public ItemModuleCommandBlock(String s)
	{
		super(s);
		
		moduleConfig.add(cs_command);
		cs_command.defaultString = "say Hello!";
	}
	
	public int getChannelCount()
	{ return 1; }
	
	public IOType getChannelType(int c)
	{ return IOType.INPUT; }
	
	public void loadRecipes()
	{
	}
	
	public void onChannelToggled(EventChannelToggled e)
	{
		if(!e.isEnabled(0, e.channel, false)) return;
		
		String cmd = cs_command.get(e.item());
		
		if(!cmd.isEmpty())
		{
			MinecraftServer ms = MinecraftServer.getServer();

			if(ms != null && ms.isCommandBlockEnabled())
			{
				ICommandManager icm = ms.getCommandManager();
				icm.executeCommand(new CmdModuleICS(e.board), cmd);
			}
		}
	}
	
	public static class CmdModuleICS implements ICommandSender // TileEntityCommandBlock
	{
		public final CircuitBoard board;
		
		public CmdModuleICS(CircuitBoard t)
		{ board = t; }
		
		public String getCommandSenderName()
		{ return "@"; }
		
		public IChatComponent func_145748_c_()
		{ return null; }
		
		public void addChatMessage(IChatComponent c)
		{ }
		
		public boolean canCommandSenderUseCommand(int i, String s)
		{ return true; }
		
		public ChunkCoordinates getPlayerCoordinates()
		{ return new ChunkCoordinates(board.cable.xCoord, board.cable.yCoord, board.cable.zCoord); }
		
		public World getEntityWorld()
		{ return board.cable.getWorldObj(); }
	}
}