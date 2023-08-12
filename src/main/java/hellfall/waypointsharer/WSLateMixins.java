package hellfall.waypointsharer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class WSLateMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.waypointsharer.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        List<String> mixins = new ArrayList<>();
        if (loadedMods.contains("journeymap")) {
            mixins.add("journeymap.WaypointManagerItemMixin");
        }
        if (loadedMods.contains("XaeroMinimap")) {
            mixins.add("xaero.ForgeEventHandlerMixin");
        }
        return mixins;
    }
}
