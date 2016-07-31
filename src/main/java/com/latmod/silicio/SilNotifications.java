package com.latmod.silicio;

import com.feed_the_beast.ftbl.api.notification.NotificationID;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LatvianModder on 31.07.2016.
 */
public class SilNotifications
{
    public static final int LINKED_WITH_CB = get("linked_with_cb");

    private static int get(String id)
    {
        return NotificationID.get(new ResourceLocation(Silicio.MOD_ID, id));
    }
}
