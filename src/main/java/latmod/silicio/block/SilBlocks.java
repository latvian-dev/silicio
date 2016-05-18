package latmod.silicio.block;

import latmod.silicio.Silicio;

/**
 * Created by LatvianModder on 02.05.2016.
 */
public class SilBlocks
{
    public static final BlockSilBlocks BLOCKS = Silicio.mod.register("blocks", new BlockSilBlocks());
    public static final BlockSilMachines MACHINES = Silicio.mod.register("machines", new BlockSilMachines());
    public static final BlockCBSocketBlock CB_SOCKET = Silicio.mod.register("socket_block", new BlockCBSocketBlock());
    public static final BlockConnector CONNECTOR = Silicio.mod.register("connector", new BlockConnector());
    public static final BlockBlueGoo BLUE_GOO = Silicio.mod.register("blue_goo", new BlockBlueGoo());
    public static final BlockAntimatterCarpet ANTIMATTER_CARPET = Silicio.mod.register("antimatter_carpet", new BlockAntimatterCarpet());
    public static final BlockTurret TURRET = Silicio.mod.register("turret", new BlockTurret());
    
    public static void init()
    {
    }
}