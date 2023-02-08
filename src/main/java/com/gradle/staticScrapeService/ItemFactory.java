package com.gradle.staticScrapeService;

import java.util.ArrayList;

public class ItemFactory extends EntityFactory {

    public Item createEntity(String itemName, ArrayList<String> fullContent) {
        // Split source, typeRarity, and description into separate categories
        String source = super.extractSource(fullContent);
        String typeRarity = fullContent.remove(TOP_I);

        String description = getDescription(fullContent);

        return new Item(itemName, source, typeRarity, description);
    }

}
