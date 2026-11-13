package digitalanxiety.squidremover.mixin;
import net.minecraft.entity.EntitySquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(EntitySquid.class)
public abstract class EntitySquidMixin {
 @Shadow public abstract void setDead();
 @Inject(method="tick", at=@At("HEAD"))
 private void squidremover$removeSquid(CallbackInfo ci) {
  EntitySquid squid=(EntitySquid)(Object)this;
  if (!squid.world.isClientSide) this.setDead();
 }
}