package sh.talonfloof.vulpine.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import sh.talonfloof.vulpine.registry.ItemRegistration;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ItemRegistration.FOX_O_METER).pattern("nrg").pattern(" ir").pattern("i n")
                .input('n', Items.IRON_NUGGET)
                .input('r', Items.REDSTONE)
                .input('i', Items.IRON_INGOT)
                .input('g', Blocks.TINTED_GLASS)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_NUGGET),FabricRecipeProvider.conditionsFromItem(Items.IRON_NUGGET))
                .criterion(FabricRecipeProvider.hasItem(Items.REDSTONE),FabricRecipeProvider.conditionsFromItem(Items.REDSTONE))
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .criterion(FabricRecipeProvider.hasItem(Blocks.TINTED_GLASS),FabricRecipeProvider.conditionsFromItem(Blocks.TINTED_GLASS))
                .offerTo(exporter);
    }
}
