package com.latmod.silicio.gui;

import com.feed_the_beast.ftbl.api.client.FTBLibClient;
import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.config.IConfigValue;
import com.feed_the_beast.ftbl.api.gui.GuiContainerWrapper;
import com.feed_the_beast.ftbl.api.gui.GuiHelper;
import com.feed_the_beast.ftbl.api.gui.GuiIcons;
import com.feed_the_beast.ftbl.api.gui.GuiLM;
import com.feed_the_beast.ftbl.api.gui.IGui;
import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.api.gui.widgets.ButtonLM;
import com.latmod.lib.client.TextureCoords;
import com.latmod.silicio.Silicio;
import com.latmod.silicio.api.module.IModuleContainer;
import com.latmod.silicio.api_impl.SilCaps;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LatvianModder on 25.08.2016.
 */
public class GuiModuleIO extends GuiLM implements IContainerListener
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Silicio.MOD_ID, "textures/gui/module_io.png");

    private enum EnumMode
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

    private class ButtonProperty extends ButtonLM
    {
        private final IConfigKey key;
        private final IConfigValue value;

        public ButtonProperty(IConfigKey k, IConfigValue v)
        {
            super(0, 0, 16, 16);
            key = k;
            value = v;
            setTitle(key.getDisplayName().getFormattedText());
        }

        @Override
        public void onClicked(IGui gui, IMouseButton button)
        {
            GuiHelper.playClickSound();
        }

        @Override
        public void renderWidget(IGui gui)
        {
            render(GuiIcons.RS_HIGH);
        }
    }

    private final ContainerModuleIO container;
    private final ButtonLM buttonMode;
    private EnumMode mode = EnumMode.BOTH;
    private IModuleContainer module;
    private final List<ButtonProperty> propertyList;

    public GuiModuleIO(ContainerModuleIO c)
    {
        super(176, 158);
        container = c;

        buttonMode = new ButtonLM(13, 54, 16, 16)
        {
            @Override
            public void onClicked(IGui gui, IMouseButton button)
            {
                GuiHelper.playClickSound();
                mode = mode.next(button.isLeft());
                gui.refreshWidgets();
            }
        };

        buttonMode.setTitle("Mode"); //TODO: Lang

        propertyList = new ArrayList<>();
    }

    @Override
    public void onInit()
    {
        container.addListener(this);
    }

    @Override
    public void addWidgets()
    {
        add(buttonMode);

        module = null;
        propertyList.clear();

        if(container.tile.itemHandler.getStackInSlot(0) != null && container.tile.itemHandler.getStackInSlot(0).hasCapability(SilCaps.MODULE_CONTAINER, null))
        {
            module = container.tile.itemHandler.getStackInSlot(0).getCapability(SilCaps.MODULE_CONTAINER, null);
        }

        if(module != null)
        {
            int i = 0;
            for(Map.Entry<IConfigKey, IConfigValue> entry : module.getProperties().getTree().entrySet())
            {
                ButtonProperty b = new ButtonProperty(entry.getKey(), entry.getValue());
                propertyList.add(b);
                b.posX = 40 + 16 * (i % 8);
                b.posY = 8 + 16 * (i / 8);
                i++;
            }
        }

        addAll(propertyList);
    }

    @Override
    public void drawBackground()
    {
        FTBLibClient.setTexture(TEXTURE);
        int ax = getAX();
        int ay = getAY();
        GuiScreen.drawModalRectWithCustomSizedTexture(ax, ay, 0F, 0F, getWidth(), getHeight(), 256F, 256F);

        buttonMode.render(mode.tex);
    }

    @Override
    public GuiScreen getWrapper()
    {
        return new GuiContainerWrapper(this, container);
    }

    @Override
    public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList)
    {
        refreshWidgets();
    }

    @Override
    public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack)
    {
        refreshWidgets();
    }

    @Override
    public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue)
    {
    }

    @Override
    public void sendAllWindowProperties(Container containerIn, IInventory inventory)
    {
    }
}