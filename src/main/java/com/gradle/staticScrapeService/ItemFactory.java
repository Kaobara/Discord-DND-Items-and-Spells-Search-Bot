package com.gradle.staticScrapeService;

import java.util.ArrayList;

public class ItemFactory extends EntityFactory {

    public Item createItem(String itemName, ArrayList<String> fullContent) {
        String source = super.extractSource(fullContent);
        String typeRarity = fullContent.remove(TOP_I);
        // Split source, levelSchool, metadata, and spellList into separate categories

        String description = getDescription(fullContent);

        return new Item(itemName, source, typeRarity, description);
    }

}
