package hellfall.waypointsharer.journeymap;

import hellfall.waypointsharer.WaypointSharer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class AddWaypointCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ws-jm-add-waypoint";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "do not use";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String arg = String.join(" ", args);
        if (arg.startsWith("xaero-waypoint:")) {
            WaypointCoder.readXaeroWaypoint(arg);
        }
        else if (arg.startsWith(WaypointSharer.JM_WAYPOINT_KEY)) {
            WaypointCoder.readJMWaypoint(arg);
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
