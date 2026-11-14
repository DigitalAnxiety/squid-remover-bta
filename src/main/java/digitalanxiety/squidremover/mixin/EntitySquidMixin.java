package digitalanxiety.squidremover.mixin;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobSquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Removes any squid that enters the entity update loop, including squids
 * already present in existing worlds.
 */
@Mixin(value = MobSquid.class, remap = false)
public abstract class EntitySquidMixin {

    @Inject(method = "onLivingUpdate", at = @At("HEAD"), cancellable = true)
    private void squidremover$removeSquid(CallbackInfo ci) {
        ((Entity) (Object) this).remove();
        ci.cancel();
    }
}
