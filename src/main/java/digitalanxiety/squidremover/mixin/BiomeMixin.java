package digitalanxiety.squidremover.mixin;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.entity.animal.MobSquid;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;

/**
 * Removes squid from every biome's natural water-creature spawn list.
 */
@Mixin(value = Biome.class, remap = false)
public abstract class BiomeMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void squidremover$removeSquidSpawns(CallbackInfo ci) {
        Biome biome = (Biome) (Object) this;
        List<SpawnListEntry> waterCreatures = biome.getSpawnableList(MobCategory.WATER_CREATURE);

        if (waterCreatures == null) {
            return;
        }

        try {
            Iterator<SpawnListEntry> entries = waterCreatures.iterator();
            while (entries.hasNext()) {
                if (entries.next().entityClass == MobSquid.class) {
                    entries.remove();
                }
            }
        } catch (UnsupportedOperationException ignored) {
            // Some special biomes may expose immutable spawn lists.
            // EntitySquidMixin still removes any squid that reaches the tick loop.
        }
    }
}
