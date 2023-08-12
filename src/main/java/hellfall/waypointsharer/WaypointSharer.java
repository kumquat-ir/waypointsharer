package hellfall.waypointsharer;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import hellfall.waypointsharer.journeymap.AddWaypointCommand;
import hellfall.waypointsharer.journeymap.ChatEventHandler;

@Mod(
    modid = Tags.MODID,
    version = Tags.VERSION,
    name = Tags.MODNAME,
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*")
public class WaypointSharer {

    public static final String JM_WAYPOINT_KEY = "ws-jm-waypoint\u0091";
    // :{"xaero-waypoint", name, symbol, x, y, z, color, rotation?, yaw, "Internal-dim%{dimid}-waypoints, vp?}
    // \u0091{"ws-jm-waypoint", name, x, y, z, rgb color, dim id, vp?}

    public static final Logger LOG = LogManager.getLogger(Tags.MODID);

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("journeymap")) {
            MinecraftForge.EVENT_BUS.register(new ChatEventHandler());
        }
    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("journeymap")) {
            ClientCommandHandler.instance.registerCommand(new AddWaypointCommand());
        }
    }

    @Mod.EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {

    }
}
