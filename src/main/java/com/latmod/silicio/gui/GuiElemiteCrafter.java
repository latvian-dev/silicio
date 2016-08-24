package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.gui.GuiLM;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 25.08.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiElemiteCrafter extends GuiLM
{
    private final ContainerElemiteCrafter container;

    public GuiElemiteCrafter(ContainerElemiteCrafter c)
    {
        super(176, 166);
        container = c;
    }

    @Override
    public void addWidgets()
    {
    }
}