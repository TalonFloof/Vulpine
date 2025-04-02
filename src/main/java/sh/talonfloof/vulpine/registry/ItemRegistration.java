package sh.talonfloof.vulpine.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import sh.talonfloof.vulpine.item.FoxOMeterItem;

import static sh.talonfloof.vulpine.Vulpine.MOD_ID;

public class ItemRegistration {
    public static Item register(String id, Item item) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

    public static final Item FOX_O_METER = register("fox_o_meter",new FoxOMeterItem(new Item.Settings()));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
             content.add(FOX_O_METER);
        });
    }
}
