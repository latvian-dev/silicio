package com.latmod.silicio.api;

import com.latmod.silicio.api.modules.Module;
import com.latmod.silicio.api.tile.ISilNetController;
import com.latmod.silicio.api.tile.ISilNetTile;
import com.latmod.silicio.api.tile.energy.ISilEnergyTank;
import com.latmod.silicio.api.tile.energy.SilEnergyTank;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Created by LatvianModder on 15.05.2016.
 */
public class SilCapabilities
{
    @CapabilityInject(Module.class)
    public static Capability<Module> MODULE = null;
    @CapabilityInject(ISilEnergyTank.class)
    public static Capability<ISilNetTile> SILNET_TILE = null;
    @CapabilityInject(ISilEnergyTank.class)
    public static Capability<ISilNetController> SILNET_CONTROLLER = null;
    @CapabilityInject(ISilEnergyTank.class)
    public static Capability<ISilEnergyTank> ENERGY_TANK = null;
    private static boolean enabled = false;

    /**
     * Call this if you use the API
     */
    public static void enable()
    {
        if(enabled)
        {
            return;
        }

        enabled = true;

        CapabilityManager.INSTANCE.register(Module.class, new Capability.IStorage<Module>()
        {
            @Override
            public NBTBase writeNBT(Capability<Module> capability, Module instance, EnumFacing side)
            {
                return null;
            }

            @Override
            public void readNBT(Capability<Module> capability, Module instance, EnumFacing side, NBTBase base)
            {
            }
        }, () -> new Module() { });

        CapabilityManager.INSTANCE.register(ISilEnergyTank.class, new Capability.IStorage<ISilEnergyTank>()
        {
            @Override
            public NBTBase writeNBT(Capability<ISilEnergyTank> capability, ISilEnergyTank instance, EnumFacing side)
            {
                return new NBTTagDouble(instance.getEnergy());
            }

            @Override
            public void readNBT(Capability<ISilEnergyTank> capability, ISilEnergyTank instance, EnumFacing side, NBTBase base)
            {
                instance.setEnergy(((NBTTagDouble) base).getDouble());
            }
        }, () -> {
            return new SilEnergyTank(10000D);
        });
    }
}
