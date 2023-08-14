package hellfall.waypointsharer.journeymap;

import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import hellfall.waypointsharer.WaypointSharer;
import hellfall.waypointsharer.colorutils.XaeroColorConverter;
import journeymap.client.model.Waypoint;
import journeymap.client.waypoint.WaypointStore;

public class WaypointCoder {

    public static void sendWaypoint(Waypoint waypoint) {
        // spotless:off
        String message = WaypointSharer.JM_WAYPOINT_KEY +
            waypoint.getName() + "\u0091" +
            waypoint.getX() + "\u0091" +
            waypoint.getY() + "\u0091" +
            waypoint.getZ() + "\u0091" +
            waypoint.getColor() + "\u0091" +
            waypoint.getDimensions().toArray()[0];
        //spotless:on
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    public static void readJMWaypoint(String message) {
        // {"ws-jm-waypoint", name, x, y, z, rgb color, dim id, vp?}
        String[] args = message.split("\u0091");
        addWaypoint(
            args[1],
            Integer.parseInt(args[2]),
            Integer.parseInt(args[3]),
            Integer.parseInt(args[4]),
            Integer.parseInt(args[5]),
            Integer.parseInt(args[6]));
    }

    public static void readXaeroWaypoint(String message) {
        // {"xaero-waypoint", name, symbol, x, y, z, color, rotation?, yaw, "Internal-dim%{dimid}-waypoints, vp?}
        String[] args = message.split(":");
        if (args[9].startsWith("Internal-")) {
            String dim = args[9].replace("Internal-", "")
                .replace("-waypoints", "");
            int dimid = switch (dim) {
                case "Overworld" -> 0;
                case "Nether" -> -1;
                case "TheEnd" -> 1;
                default -> {
                    if (dim.startsWith("dim%")) {
                        yield Integer.parseInt(dim.substring(4));
                    } else yield 0;
                }
            };
            addWaypoint(
                args[1],
                Integer.parseInt(args[3]),
                Integer.parseInt(args[4]),
                Integer.parseInt(args[5]),
                XaeroColorConverter.xaeroToRGBColor(Integer.parseInt(args[6])),
                dimid);
        }
    }

    private static void addWaypoint(String name, int x, int y, int z, int color, int dim) {
        WaypointStore.instance()
            .save(new Waypoint(name, x, y, z, new Color(color), Waypoint.Type.Normal, dim));
        Minecraft.getMinecraft().ingameGUI.getChatGUI()
            .printChatMessage(new ChatComponentText(I18n.format("waypointsharer.addedwaypoint", name)));
    }
}
