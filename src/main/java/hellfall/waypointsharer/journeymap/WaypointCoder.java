package hellfall.waypointsharer.journeymap;

import java.awt.*;

import net.minecraft.client.Minecraft;

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
        Waypoint waypoint = new Waypoint(
            args[1],
            Integer.parseInt(args[2]),
            Integer.parseInt(args[3]),
            Integer.parseInt(args[4]),
            new Color(Integer.parseInt(args[5])),
            Waypoint.Type.Normal,
            Integer.parseInt(args[6]));
        WaypointStore.instance()
            .save(waypoint);
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
            Waypoint waypoint = new Waypoint(
                args[1],
                Integer.parseInt(args[3]),
                Integer.parseInt(args[4]),
                Integer.parseInt(args[5]),
                new Color(XaeroColorConverter.xaeroToRGBColor(Integer.parseInt(args[6]))),
                Waypoint.Type.Normal,
                dimid);
            WaypointStore.instance()
                .save(waypoint);
        }
    }
}
