package digitalanxiety.squidremover.mixin;

import net.minecraft.core.entity.animal.MobSquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Removes every squid on the logical server and client.
 * The server-side copy is authoritative in multiplayer.
 */
@Mixin(MobSquid.class)
public abstract class EntitySquidMixin {
    @Shadow
    public abstract void remove();

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    private void squidremover$removeSquid(CallbackInfo ci) {
        this.remove();
    }
}
