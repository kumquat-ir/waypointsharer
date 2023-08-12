package hellfall.waypointsharer.mixins.journeymap;

import net.minecraft.client.gui.FontRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableList;

import hellfall.waypointsharer.journeymap.WaypointCoder;
import journeymap.client.Constants;
import journeymap.client.model.Waypoint;
import journeymap.client.ui.component.Button;
import journeymap.client.ui.component.ButtonList;
import journeymap.client.ui.waypoint.WaypointManager;
import journeymap.client.ui.waypoint.WaypointManagerItem;

@SuppressWarnings("UnusedMixin")
@Mixin(value = WaypointManagerItem.class, remap = false)
public class WaypointManagerItemMixin {

    @Shadow
    ButtonList buttonListRight;
    @Shadow
    Waypoint waypoint;
    @Shadow
    Button buttonEdit;
    @Shadow
    Button buttonRemove;
    @Unique
    private Button buttonShare;

    @SuppressWarnings("InjectIntoConstructor")
    @Inject(
        method = "<init>",
        at = @At(value = "INVOKE", target = "Ljourneymap/client/ui/component/ButtonList;setHeights(I)V", ordinal = 1))
    private void waypointsharer$injectInit(Waypoint waypoint, FontRenderer fontRenderer, WaypointManager manager,
        CallbackInfo ci) {
        buttonShare = new Button(Constants.getString("waypointsharer.sharebutton"));
        if (buttonListRight.size() == 3) {
            // share where i am button is here, get rid of it
            buttonListRight.clear();
            buttonListRight.addAll(ImmutableList.of(buttonEdit, buttonRemove));
        }
        buttonListRight.add(buttonShare);
    }

    @Inject(
        method = "clickScrollable",
        at = @At(value = "INVOKE", target = "Ljourneymap/client/ui/component/Button;mouseOver(II)Z", ordinal = 0),
        cancellable = true)
    private void waypointsharer$injectButtonPress(int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        if (buttonShare.mouseOver(mouseX, mouseY)) {
            WaypointCoder.sendWaypoint(waypoint);
            cir.setReturnValue(true);
        }
    }
}
