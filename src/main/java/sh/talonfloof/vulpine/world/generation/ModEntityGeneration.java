package sh.talonfloof.vulpine.world.generation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import sh.talonfloof.vulpine.FoxVariantSelector;
import sh.talonfloof.vulpine.Vulpine;
import sh.talonfloof.vulpine.registry.ModMobTags;

/**
 * This class handle new spawn rule for biome that don't have foxes in vanilla.
 * SpawnGroup CREATURE is pool for passive / animal mobs, and MONSTER is for aggressive mobs.
 */
public class ModEntityGeneration {

    public static void addFoxSpawns(){
        //Only add biome where foxes don't naturally spawn.

        if(Vulpine.config.spawning.enableFennecFoxes.get()) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_DESERT_FOX), SpawnGroup.CREATURE,
                    EntityType.FOX, 1, 2, 4);
        }

        if(Vulpine.config.spawning.enableNetherFoxes.get()) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_NETHER_FOX), SpawnGroup.CREATURE,
                    EntityType.FOX, 1, 1, 2);
        }

        if(Vulpine.config.spawning.enableEndFoxes.get()) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_ENDER_FOX), SpawnGroup.CREATURE,
                    EntityType.FOX, 1, 1, 2);
        }

        if(Vulpine.config.spawning.enableSculkFoxes.get()) {
            BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_SCULK_FOX), SpawnGroup.CREATURE,
                    EntityType.FOX, 10, 1, 2);
        }

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_ZOMBIE_FOX), SpawnGroup.CREATURE,
                EntityType.FOX,10,1,2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_SKELETON_FOX), SpawnGroup.CREATURE,
                EntityType.FOX,10,1,2);
    }
}
