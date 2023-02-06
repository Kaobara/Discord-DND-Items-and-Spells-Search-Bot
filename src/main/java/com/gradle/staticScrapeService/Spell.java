package com.gradle.staticScrapeService;

import discord4j.core.spec.EmbedCreateFields;

import java.util.Locale;

public class Spell {
    private String name, source, levelSchool, castingTime, range, components, duration, description, upcast, spellList;
    private String URL;
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
        System.out.println(name.toUpperCase(Locale.ROOT));
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

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getLevelSchool() {
        return levelSchool;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public String getRange() {
        return range;
    }

    public String getComponents() {
        return components;
    }

    public String getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getUpcast() {
        return upcast;
    }

    public String getSpellList() {
        return spellList;
    }

    public boolean isRitual() {
        return ritual;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public boolean isCanUpcast() {
        return canUpcast;
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
