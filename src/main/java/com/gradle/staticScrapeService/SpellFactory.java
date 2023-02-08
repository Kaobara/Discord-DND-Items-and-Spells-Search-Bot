package com.gradle.staticScrapeService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SpellFactory extends EntityFactory {

    public Spell createEntity(String spellName, ArrayList<String> fullContent) {
        // Split source, levelSchool, metadata, and spellList into separate categories
        String source = super.extractSource(fullContent);
        String levelSchool = fullContent.remove(TOP_I);
        String metaData = fullContent.remove(TOP_I);
        String spellList = fullContent.remove(fullContent.size() + BOTTOM_I).replaceFirst("..Spell Lists\\...", "");

        // If the spell can be upcast, place it into its own category as well
        String upcast = "";
        if(fullContent.get(fullContent.size() + BOTTOM_I).contains("**At Higher Levels.** ")) {
            upcast = fullContent.remove(fullContent.size() + BOTTOM_I).replace("**At Higher Levels.** ", "");
        }

        // Combine the description into a single string
        String description = super.getDescription(fullContent);

        return new Spell(spellName, source, levelSchool, metaData, description, upcast, spellList);
    }

}
