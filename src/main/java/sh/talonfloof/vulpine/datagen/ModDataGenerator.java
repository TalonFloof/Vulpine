package sh.talonfloof.vulpine.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModBiomeTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModLanguageProvider::new);
    }

}
