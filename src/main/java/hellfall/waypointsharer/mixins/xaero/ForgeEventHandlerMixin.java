package hellfall.waypointsharer.mixins.xaero;

import hellfall.waypointsharer.WaypointSharer;
import hellfall.waypointsharer.colorutils.XaeroColorConverter;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xaero.common.events.ForgeEventHandler;

@SuppressWarnings("UnusedMixin")
@Mixin(ForgeEventHandler.class)
public class ForgeEventHandlerMixin {
    @Redirect(method = "handleClientChatReceivedEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/IChatComponent;getFormattedText()Ljava/lang/String;"))
    private String waypointsharer$injectChatEvent(IChatComponent instance) {
        String text = instance.getFormattedText();
        if (text.contains(WaypointSharer.JM_WAYPOINT_KEY)) {
            // :{"xaero-waypoint", name, symbol, x, y, z, color, rotation?, yaw, "Internal-dim%{dimid}-waypoints, vp?}
            // \u0091{"ws-jm-waypoint", name, x, y, z, rgb color, dim id, vp?}
            String[] args = text.substring(text.indexOf(WaypointSharer.JM_WAYPOINT_KEY)).split("\u0091");
            text = text.substring(0, text.indexOf(WaypointSharer.JM_WAYPOINT_KEY)) +
                "xaero-waypoint:" +
                args[1].replace(":", "^col^") + ":" +
                args[1].charAt(0) + ":" +
                args[2] + ":" +
                args[3] + ":" +
                args[4] + ":" +
                XaeroColorConverter.rgbToXaeroColor(Integer.parseInt(args[5])) + ":" +
                "false:0:" +
                "Internal-dim%" + args[6] + "-waypoints";
        }
        return text;
    }
}
