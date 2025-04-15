package sh.talonfloof.vulpine;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import sh.talonfloof.vulpine.registry.ModMobTags;

import java.util.ArrayList;
import java.util.List;

public abstract class FoxVariantSelector {

    private static Random rand = Random.create();

    public static List<FoxEntity.Type> SNOW_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> DEFAULT_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> DESERT_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> TAIGA_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> NETHER_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> ENDER_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> SCULK_VARIANT = new ArrayList();
    public static List<FoxEntity.Type> MONSTER_VARIANT = new ArrayList();

    private static FoxEntity.Type randomDefaultVariant(){
        return DEFAULT_VARIANT.get(rand.nextInt(DEFAULT_VARIANT.size()));
    }

    private static FoxEntity.Type randomSnowVariant(){
        return SNOW_VARIANT.get(rand.nextInt(SNOW_VARIANT.size()));
    }

    private static FoxEntity.Type randomTaigaVariant(){
        return TAIGA_VARIANT.get(rand.nextInt(TAIGA_VARIANT.size()));
    }

    private static FoxEntity.Type randomNetherVariant(){
        return NETHER_VARIANT.get(rand.nextInt(NETHER_VARIANT.size()));
    }

    private static FoxEntity.Type randomDesertVariant(){
        return DESERT_VARIANT.get(rand.nextInt(DESERT_VARIANT.size()));
    }

    private static FoxEntity.Type randomEnderVariant(){
        if(rand.nextInt(100) < 10)
            return Vulpine.TALON; //10% Chance to spawn TALON variant. I think it fit well in the END
        return ENDER_VARIANT.get(rand.nextInt(ENDER_VARIANT.size()));
    }

    private static FoxEntity.Type randomMonsterVariant(){
        return MONSTER_VARIANT.get(rand.nextInt(MONSTER_VARIANT.size()));
    }

    private static FoxEntity.Type randomSculkVariant(){
        return SCULK_VARIANT.get(rand.nextInt(SCULK_VARIANT.size()));
    }

    private static boolean shouldSpawnMonster(World foxWorld){
        DimensionType dim = foxWorld.getDimensionEntry().value();
        boolean shouldSpawnMonster = foxWorld.getRegistryKey() == World.OVERWORLD;
        shouldSpawnMonster = shouldSpawnMonster & foxWorld.isNight();
        return shouldSpawnMonster & rand.nextInt(100)<15; //15% to spawn monster version at night in overwolrd.
    }

    public static FoxEntity.Type selectFoxVariant(@NotNull FoxEntity foxEntity, RegistryEntry<Biome> foxBiome){

        if (shouldSpawnMonster(foxEntity.getEntityWorld())) {
            return randomMonsterVariant();
        }

        if (foxBiome.isIn(ModMobTags.HAS_NETHER_FOX)){
            return randomNetherVariant();
        }

        if (foxBiome.isIn(ModMobTags.HAS_DESERT_FOX)){
            return randomDesertVariant();
        }

        if (foxBiome.isIn(ModMobTags.HAS_SNOW_FOX)){
                return randomSnowVariant();
        }

        if (foxBiome.isIn(ModMobTags.HAS_TAIGA_FOX)){
            if (!foxBiome.isIn(ConventionalBiomeTags.IS_SNOWY)) {
                return randomTaigaVariant();
            } else {
                return randomSnowVariant();
            }
        }

        if (foxBiome.isIn(ModMobTags.HAS_SCULK_FOX)){
            return randomSculkVariant();
        }

        if (foxBiome.isIn(ModMobTags.HAS_ENDER_FOX)){
            return randomEnderVariant();
        }

        return randomDefaultVariant();
    }
}
