package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.gui.GuiLM;
import com.latmod.silicio.Silicio;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LatvianModder on 25.09.2016.
 */
public class GuiLamp extends GuiLM
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Silicio.MOD_ID, "textures/gui/lamp.png");

    private final ContainerLamp container;

    public GuiLamp(ContainerLamp c)
    {
        super(176, 158);
        container = c;
    }

    @Override
    public void onInit()
    {
    }

    @Override
    public void addWidgets()
    {
    }

    @Override
    public void drawBackground()
    {
        mc.getTextureManager().bindTexture(TEXTURE);
        int ax = getAX();
        int ay = getAY();
        GuiScreen.drawModalRectWithCustomSizedTexture(ax, ay, 0F, 0F, getWidth(), getHeight(), 256F, 256F);
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}