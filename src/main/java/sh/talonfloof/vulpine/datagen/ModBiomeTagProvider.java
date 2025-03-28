package sh.talonfloof.vulpine.datagen;


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import sh.talonfloof.vulpine.Vulpine;
import sh.talonfloof.vulpine.registry.ModMobTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagProvider<Biome> {


    public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BIOME, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        Vulpine.LOGGER.info("Generating Biome tags for mod : "+Vulpine.MOD_ID);

        //Some tag entries are redundant but this allows to potential compat with mode and/or configurability

        getOrCreateTagBuilder(ModMobTags.HAS_NETHER_FOX)
                .forceAddTag(ConventionalBiomeTags.IS_NETHER_FOREST)
                .add(BiomeKeys.SOUL_SAND_VALLEY)
                .add(BiomeKeys.NETHER_WASTES);

        getOrCreateTagBuilder(ModMobTags.HAS_DESERT_FOX)
                .forceAddTag(ConventionalBiomeTags.IS_DESERT)
                .add(BiomeKeys.BADLANDS);

        getOrCreateTagBuilder(ModMobTags.HAS_SNOW_FOX)
                .forceAddTag(BiomeTags.SPAWNS_SNOW_FOXES);

        getOrCreateTagBuilder(ModMobTags.HAS_TAIGA_FOX)
                .forceAddTag(ConventionalBiomeTags.IS_TAIGA);

        getOrCreateTagBuilder(ModMobTags.HAS_ENDER_FOX)
                .forceAddTag(ConventionalBiomeTags.IS_OUTER_END_ISLAND)
                .add(BiomeKeys.THE_END);

        getOrCreateTagBuilder(ModMobTags.HAS_SCULK_FOX)
                .add(BiomeKeys.DEEP_DARK);

    }
}
