package com.gradle.staticScrapeService;

import java.util.ArrayList;
import java.util.Arrays;

public class SpellFactory {
    final private int TOP_I = 0;
    final private int UPCAST_I = -2;
//    final private int SPELL_LIST_I = -1;
    final private int BOTTOM_I = -1;

    private ArrayList<String> contentList;

    public Spell createSpell(String spellName, String fullContent) {
        contentList = sortContents(fullContent);

        String source = content(TOP_I, "Source: ");
        String levelSchool = content(TOP_I, "");
        String castingTime = content(TOP_I, "Casting Time: ");
        String range = content(TOP_I, "Range: ");
        String components = content(TOP_I, "Components: ");
        String duration = content(TOP_I, "Duration: ");

        String spellList = content(contentList.size() + BOTTOM_I, "Spell Lists. ");

        String upcast = "";
        if(contentList.get(contentList.size() + BOTTOM_I).contains("At Higher Levels. ")) {
            upcast = content(contentList.size() + BOTTOM_I, "At Higher Levels. ");
        }

        String description = "";
        for(String content : contentList) {
            if(description.isEmpty()) {
                description = content;
            } else {
                description += "\n" + content;
            }
        }


        Spell spell = new Spell(spellName, source, levelSchool, castingTime, range, components, duration,
                description, upcast, spellList);
        return spell;

    }

    // Split the contents into an array list, per line
    public ArrayList<String> sortContents(String fullContent) {
        String[] strSplit = fullContent.split("\n");

        ArrayList<String> contentList = new ArrayList<>(
                Arrays.asList(strSplit)
        );

        contentList.removeAll(Arrays.asList("", null));
        return contentList;
    }

    private String content(int contentIndex, String stringRemoval) {
        String content;
        if(stringRemoval != "") {
            content = contentList.get(contentIndex).replaceFirst(stringRemoval, "");
        } else {
            content = contentList.get(contentIndex);
        }
        contentList.remove(contentIndex);
        return content;
    }

}
