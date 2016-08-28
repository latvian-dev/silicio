package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.gui.GuiLM;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.api.gui.widgets.ButtonLM;
import com.latmod.lib.TextureCoords;
import com.latmod.silicio.Silicio;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 25.08.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiModuleIO extends GuiLM
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Silicio.MOD_ID, "textures/gui/module_io.png");

    public enum EnumMode
    {
        BOTH,
        CHANNELS,
        CONFIG;

        public static final EnumMode[] VALUES = values();
        public final TextureCoords tex;

        EnumMode()
        {
            tex = TextureCoords.fromCoords(TEXTURE, 176, ordinal() * 16, 16, 16, 256, 256);
        }

        public EnumMode next(boolean f)
        {
            int i;

            if(f)
            {
                i = (ordinal() + 1) % VALUES.length;
            }
            else
            {
                i = ordinal() - 1;

                if(i < 0)
                {
                    i = VALUES.length - 1;
                }
            }

            return VALUES[i];
        }
    }

    private final ContainerModuleIO container;
    private final ButtonLM buttonMode;
    private EnumMode mode = EnumMode.BOTH;

    public GuiModuleIO(ContainerModuleIO c)
    {
        super(176, 158);
        container = c;

        buttonMode = new ButtonLM(13, 54, 16, 16)
        {
            @Override
            public void onClicked(GuiLM gui, IMouseButton button)
            {
                playClickSound();
                mode = mode.next(button.isLeft());
                gui.refreshWidgets();
            }
        };

        buttonMode.setTitle("Mode"); //TODO: Lang
    }

    @Override
    public void addWidgets()
    {
        add(buttonMode);
    }

    @Override
    public void drawBackground()
    {
        FTBLibClient.setTexture(TEXTURE);
        int ax = getAX();
        int ay = getAY();
        GuiScreen.drawModalRectWithCustomSizedTexture(ax, ay, 0F, 0F, width, height, 256F, 256F);

        buttonMode.render(mode.tex);
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }
}