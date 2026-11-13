package digitalanxiety.squidremover.mixin;

import net.minecraft.src.EntitySquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Removes every squid as soon as it updates. This runs on both logical sides,
 * so the server removes the actual entity in multiplayer as well.
 */
@Mixin(EntitySquid.class)
public abstract class EntitySquidMixin {
    @Shadow
    public abstract void setDead();

    @Inject(method = "onUpdate", at = @At("HEAD"))
    private void squidremover$removeSquid(CallbackInfo ci) {
        this.setDead();
    }
}
