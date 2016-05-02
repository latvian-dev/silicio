package latmod.silicio;

import latmod.silicio.block.BlockAntimatterCarpet;
import latmod.silicio.block.BlockBlueGoo;
import latmod.silicio.block.BlockCBSocketBlock;
import latmod.silicio.block.BlockCoil;
import latmod.silicio.block.BlockLaserIO;
import latmod.silicio.block.BlockLaserMirrorBox;
import latmod.silicio.block.BlockSilBlocks;
import latmod.silicio.block.BlockSilMachines;
import latmod.silicio.block.BlockTurret;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class SilBlocks
{
	public static final BlockSilBlocks BLOCKS = Silicio.mod.register("blocks", new BlockSilBlocks());
	public static final BlockSilMachines MACHINES = Silicio.mod.register("machines", new BlockSilMachines());
	public static final BlockCBSocketBlock CB_SOCKET = Silicio.mod.register("socket_block", new BlockCBSocketBlock());
	public static final BlockCoil COIL = Silicio.mod.register("coil", new BlockCoil());
	public static final BlockBlueGoo BLUE_GOO = Silicio.mod.register("blue_goo", new BlockBlueGoo());
	public static final BlockAntimatterCarpet ANTIMATTER_CARPET = Silicio.mod.register("antimatter_carpet", new BlockAntimatterCarpet());
	public static final BlockLaserMirrorBox LASER_MIRROR = Silicio.mod.register("laser_mirror_box", new BlockLaserMirrorBox());
	public static final BlockLaserIO LASER_RX = Silicio.mod.register("laser_rx", new BlockLaserIO(true));
	public static final BlockLaserIO LASER_TX = Silicio.mod.register("laser_tx", new BlockLaserIO(false));
	public static final BlockTurret TURRET = Silicio.mod.register("turret", new BlockTurret());
	
	public static void init()
	{
	}
}