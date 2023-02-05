package com.gradle.staticScrapeService;

import java.util.ArrayList;
import java.util.Arrays;

public class Spell {
    private String name, source, levelSchool, castingTime, range, components, duration, description, upcast, spellList;
    private boolean ritual = false, concentration = false, canUpcast = false;

    public Spell(String name, String source, String levelSchool, String castingTime,
                 String range, String components, String duration, String description,
                 String upcast, String spellList) {
        this.name = name;
        this.source = source;

        this.levelSchool = levelSchool;
        if(this.levelSchool.contains("ritual")) {
            ritual = true;
        }

        this.castingTime = castingTime;
        this.range = range;
        this.components = components;

        this.duration = duration;
        if(this.duration.contains("Concentration")) {
            concentration = true;
        }

        this.description = description;

        this.upcast = upcast;
        if(!this.upcast.isEmpty()) {
            canUpcast = true;
        }
        this.spellList = spellList;
    }

    public void printContents() {
        System.out.println(name);
        System.out.println(source);
        System.out.println(levelSchool);
        System.out.println(castingTime);
        System.out.println(range);
        System.out.println(components);
        System.out.println(duration);
        System.out.println(description);
        if(canUpcast){
            System.out.println(upcast);
        }
        System.out.println(spellList);
    }

    public String getContents() {
        String content = name + "\nsource: " + source + "\n" + levelSchool + "\nCasting Time: " + castingTime +
                "\nRange: " + range + "\nComponents: " + components + "\nDuration: " + duration + "\n" + description;
        if(canUpcast) {
            content += "\nAt Higher Levels. " + upcast;
        }
        content += "\nSpellList. " + spellList;
        return content;
    }
}
