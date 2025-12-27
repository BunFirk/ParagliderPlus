package org.eatbun.paragliderplus.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.eatbun.paragliderplus.item.custom.ParagliderDefaultItem;

import static org.eatbun.paragliderplus.Paragliderplus.MOD_ID;

public class ModItems {
    public static final Item PARAGLIDER = registerItem("paraglider",
            new ParagliderDefaultItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ent -> {
            ent.add(PARAGLIDER);
        });
    }
}
