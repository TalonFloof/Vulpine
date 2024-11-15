package sh.talonfox.vulpine.world.generation;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.world.Heightmap;
import sh.talonfox.vulpine.registry.ModMobTags;

/**
 * This class handle new spawn rule for biome that don't have foxes in vanilla.
 * SpawnGroup CREATURE is pool for passive / animal mobs, and MONSTER is for aggressive mobs.
 */
public class ModEntityGeneration {

    public static void addFoxSpawns(){
        //Only add biome where foxes don't naturally spawn.

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_DESERT_FOX), SpawnGroup.CREATURE,
                EntityType.FOX,35,2,4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_NETHER_FOX), SpawnGroup.MONSTER,
                EntityType.FOX,35,2,4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_ENDER_FOX), SpawnGroup.MONSTER,
                EntityType.FOX,5,2,4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_SCULK_FOX), SpawnGroup.MONSTER,
                EntityType.FOX,10,1,2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_ZOMBIE_FOX), SpawnGroup.MONSTER,
                EntityType.FOX,25,1,2);

        BiomeModifications.addSpawn(BiomeSelectors.tag(ModMobTags.HAS_SKELETON_FOX), SpawnGroup.MONSTER,
                EntityType.FOX,25,1,2);
    }
}
