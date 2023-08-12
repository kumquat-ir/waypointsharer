package hellfall.waypointsharer.journeymap;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import hellfall.waypointsharer.WaypointSharer;

public class ChatEventHandler {

    @SubscribeEvent
    public void handleChatEvent(ClientChatReceivedEvent event) {
        String text = event.message.getUnformattedText();
        String key = null;
        Character delimiter = null;
        if (text.contains("xaero-waypoint:")) {
            key = "xaero-waypoint:";
            delimiter = ':';
        } else if (text.contains(WaypointSharer.JM_WAYPOINT_KEY)) {
            key = WaypointSharer.JM_WAYPOINT_KEY;
            delimiter = '\u0091';
        }
        if (key != null) {
            String playerBrackets = text.substring(0, text.indexOf(key));
            String player = playerBrackets.substring(1, playerBrackets.lastIndexOf('>'));
            String name = text.substring(text.indexOf(key) + 15);
            IChatComponent component = new ChatComponentTranslation(
                "waypointsharer.sharemessage",
                player,
                name.substring(0, name.indexOf(delimiter)));
            text = text.substring(text.indexOf(key));
            component.getChatStyle()
                .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ws-jm-add-waypoint " + text));
            event.message = component;
        }
    }
}
