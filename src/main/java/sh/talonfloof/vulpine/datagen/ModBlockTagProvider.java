package sh.talonfloof.vulpine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;


public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    /**
     * Fox have a very limited blocks list on which they can spawn. This list allow spawn of different biomes.
     * @param wrapperLookup
     */
    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(BlockTags.FOXES_SPAWNABLE_ON)
                .add(Blocks.NETHERRACK)
                .add(Blocks.SCULK)
                .add(Blocks.SCULK_VEIN)
                .add(Blocks.END_STONE)
                .forceAddTag(BlockTags.NYLIUM)
                .add(Blocks.SOUL_SAND)
                .add(Blocks.SOUL_SOIL)
                .forceAddTag(BlockTags.SAND);
    }
}
