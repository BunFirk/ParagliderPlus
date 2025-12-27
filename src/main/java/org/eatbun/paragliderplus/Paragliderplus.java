package org.eatbun.paragliderplus;

import net.fabricmc.api.ModInitializer;

import static org.eatbun.paragliderplus.item.ModItems.registerModItems;

public class Paragliderplus implements ModInitializer {
    public static final String MOD_ID = "paragliderplus";

    @Override
    public void onInitialize() {
        registerModItems();
    }
}