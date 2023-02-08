package com.gradle.staticScrapeService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SpellFactory {
    final private int TOP_I = 0;
    final private int UPCAST_I = -2;
//    final private int SPELL_LIST_I = -1;
    final private int BOTTOM_I = -1;

    private ArrayList<String> contentList;

    public Spell createSpell(String spellName, ArrayList<String> fullContent) {
        // Split source, levelSchool, metadata, and spellList into separate categories
        String source = fullContent.remove(TOP_I).replaceFirst("Source: ", "");
        String levelSchool = "_" + fullContent.remove(TOP_I) + "_";
        String metaData = formatMetaData(fullContent.remove(TOP_I));
        String spellList = fullContent.remove(fullContent.size() + BOTTOM_I).replaceFirst("Spell Lists. ", "");

        // If the spell can be upcast, place it into its own category as well
        String upcast = "";
        if(fullContent.get(fullContent.size() + BOTTOM_I).contains("At Higher Levels. ")) {
            upcast = fullContent.remove(fullContent.size() + BOTTOM_I).replaceFirst("At Higher Levels. ", "");
        }

        // Combine the description into a single string
        String description = "";
        for(String content : fullContent) {
            if(description.isEmpty()) {
                description = content;
            } else {
                if(content.startsWith("-")) { description += "\n" + content;}
                else {description += "\n\n" + content;}
            }
        }

        return new Spell(spellName, source, levelSchool, metaData, description, upcast, spellList);
    }

    public String formatMetaData(String metaData) {
        // We want to bold the metadata type names for each line
        // this is done by adding "**" to the start and to the end of the metadata type names (e.g. "**Duration:**)

        // Regex - split after a ":", but include the delimiter
        String regexDelimiter = "(?<=:)";

        // Split the entire metadata into its own lines
        String[] tempDataArr = metaData.split("\n");
        String finalMetaData = "";

        for(String metaDataLine : tempDataArr) {
            // Add the first set of **
            metaDataLine = "**"+metaDataLine;

            // Add second set of ** after the metadata type names
            String[] tempDataLineArr = metaDataLine.split(regexDelimiter, 2);
            metaDataLine = metaDataLine.replace(tempDataLineArr[0], tempDataLineArr[0]+"**");
            finalMetaData += metaDataLine + "\n";
        }
        return finalMetaData;

    }

}
