package com.gradle.staticScrapeService;

import java.util.ArrayList;
import java.util.Arrays;

public class Spell {
    private String name, source, school, level, castingTime, range, components, duration, description, higherLevels, spellList;
    private boolean ritual, concentration, canUpcast;
    private String fullContent;

    public Spell(String name, String fullContent) {
        this.name = name;
        this.fullContent = fullContent;
    }

    public void printNameContent() {
        System.out.println(name + "\n" + fullContent);
    }



    public void setSource(String source) {
        this.source = source;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setCastingTime(String castingTime) {
        this.castingTime = castingTime;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHigherLevels(String higherLevels) {
        this.higherLevels = higherLevels;
    }

    public void setSpellList(String spellList) {
        this.spellList = spellList;
    }

    public void setCanUpcast(boolean canUpcast) {
        this.canUpcast = canUpcast;
    }
}
