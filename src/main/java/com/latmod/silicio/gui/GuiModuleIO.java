package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.gui.IGui;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.lib.client.TextureCoords;
import com.feed_the_beast.ftbl.lib.gui.ButtonLM;
import com.feed_the_beast.ftbl.lib.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.lib.gui.GuiHelper;
import com.feed_the_beast.ftbl.lib.gui.GuiLM;
import com.feed_the_beast.ftbl.lib.gui.WidgetLM;
import com.latmod.silicio.Silicio;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class GuiModuleIO extends GuiLM
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Silicio.MOD_ID, "textures/gui/module_io.png");
    private static final TextureCoords PROGRESS_BAR = TextureCoords.fromCoords(TEXTURE, 176, 0, 20, 16, 256, 256);
    private static final TextureCoords ENERGY_BAR = TextureCoords.fromCoords(TEXTURE, 176, 16, 33, 16, 256, 256);

    private final ContainerModuleIO container;
    private final WidgetLM progressBar, energyBar;

    public GuiModuleIO(ContainerModuleIO c)
    {
        super(176, 121);
        container = c;

        progressBar = new ButtonLM(86, 11, 20, 16)
        {
            @Override
            public void onClicked(IGui gui, IMouseButton button)
            {
                GuiHelper.playClickSound();
                mc.playerController.sendEnchantPacket(container.windowId, 0);
            }

            @Override
            public String getTitle(IGui gui)
            {
                return container.tile.progress + "%";
            }

            @Override
            public void renderWidget(IGui gui)
            {
                double size = (int) (container.tile.progress * 21D / 100D) / 20D;
                double minU = PROGRESS_BAR.getMinU();
                double maxU = PROGRESS_BAR.getMaxU();
                GuiHelper.drawTexturedRect(getAX(), getAY(), (int) (getWidth() * size), getHeight(), minU, PROGRESS_BAR.getMinV(), minU + (maxU - minU) * size, PROGRESS_BAR.getMaxV());
            }
        };

        energyBar = new WidgetLM(9, 11, 33, 16)
        {
            @Override
            public String getTitle(IGui gui)
            {
                return (int) (container.tile.energy * 100F / 3200F) + "%";
            }

            @Override
            public void renderWidget(IGui gui)
            {
                double size = (int) (container.tile.energy * 34D / 3200D) / 33D;
                double minU = ENERGY_BAR.getMinU();
                double maxU = ENERGY_BAR.getMaxU();
                GuiHelper.drawTexturedRect(getAX(), getAY(), (int) (getWidth() * size), getHeight(), minU, ENERGY_BAR.getMinV(), minU + (maxU - minU) * size, ENERGY_BAR.getMaxV());
            }
        };
    }

    @Override
    public void addWidgets()
    {
        add(progressBar);
        add(energyBar);
    }

    @Override
    public void drawBackground()
    {
        FTBLibClient.setTexture(TEXTURE);
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