package com.gradle.staticScrapeService;

import java.util.ArrayList;
import java.util.Arrays;

public class SpellFactory {
    final private int SOURCE_I = 0;
    final private int LEVEL_TYPE_I = 1;
    final private int CASTING_TIME_I = 2;
    final private int RANGE_I = 3;
    final private int COMPONENTS_I = 4;
    final private int DURATION_I = 5;
    final private int UPCAST = -2;
    final private int SPELL_LIST = -1;

    public Spell createSpell(String spellName, String fullContent) {
        ArrayList<String> contentList = sortContents(fullContent);

//        contentList.

        String source = contentList.get(SOURCE_I).replaceFirst("Source: ", "");
        String levelType = contentList.get(LEVEL_TYPE_I);
        String castingTime = contentList.get(CASTING_TIME_I).replaceFirst("Casting Time: ", "");
        String range = contentList.get(RANGE_I).replaceFirst("Range: ", "");
        String components = contentList.get(SOURCE_I).replaceFirst("Components : ", "");
        String duration = contentList.get(SOURCE_I).replaceFirst("Duration: ", "");
        if(contentList.get(UPCAST).contains("At Higher Levels. ")) {
            String upcast = contentList.get(UPCAST).replaceFirst("At Higher Levels. ", "");
        }
        String spellList = contentList.get(SPELL_LIST).replaceFirst("Spell Lists. ", "");


        Spell spell = new Spell(spellName, fullContent);
        return spell;

    }

    // Split the contents into an array list, per line
    public ArrayList<String> sortContents(String fullContent) {
        String[] strSplit = fullContent.split("\n");

        ArrayList<String> contentList = new ArrayList<>(
                Arrays.asList(strSplit)
        );

        // Remove all empty lines
        contentList.removeAll(Arrays.asList("", null));
        return contentList;
    }

    private String content(int contentIndex, ArrayList<String> contentList, String stringRemoval) {
        String content = contentList.get(contentIndex).replaceFirst(stringRemoval, "");
        return content;
    }
}
