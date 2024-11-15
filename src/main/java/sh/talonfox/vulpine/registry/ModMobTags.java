package sh.talonfox.vulpine.registry;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import sh.talonfox.vulpine.Vulpine;

public class ModMobTags {

    public static final TagKey<Biome> HAS_NETHER_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_nether_fox"));
    public static final TagKey<Biome> HAS_SCULK_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_sculk_fox"));
    public static final TagKey<Biome> HAS_DESERT_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_desert_fox"));
    public static final TagKey<Biome> HAS_SNOW_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_snow_fox"));
    public static final TagKey<Biome> HAS_TAIGA_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_taiga_fox"));
    public static final TagKey<Biome> HAS_ENDER_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_ended_fox"));
    public static final TagKey<Biome> HAS_ZOMBIE_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_zombie_fox"));
    public static final TagKey<Biome> HAS_SKELETON_FOX = TagKey.of(RegistryKeys.BIOME, Identifier.of(Vulpine.MOD_ID,"has_skeleton_fox"));

    public static void registerModTags(){
        Vulpine.LOGGER.info("Generating mob tags for mod : "+Vulpine.MOD_ID);
    }
}
