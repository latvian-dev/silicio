package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.gui.GuiLM;
import com.latmod.silicio.Silicio;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 25.08.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiElemiteCrafter extends GuiLM
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Silicio.MOD_ID, "textures/gui/elemite_crafter.png");
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

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }

    @Override
    public void drawBackground()
    {
        FTBLibClient.setTexture(TEXTURE);
        int ax = getAX();
        int ay = getAY();
        GuiScreen.drawModalRectWithCustomSizedTexture(ax, ay, 0F, 0F, width, height, 256F, 256F);
    }
}