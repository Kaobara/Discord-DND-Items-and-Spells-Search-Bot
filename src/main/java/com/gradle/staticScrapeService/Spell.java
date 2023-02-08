package com.gradle.staticScrapeService;

import discord4j.core.spec.EmbedCreateFields;

import java.util.Locale;

public class Spell {
    private String name, source, levelSchool, metadata, description, upcast, spellList;
    private String URL;
    private boolean ritual = false, concentration = false, canUpcastBool = false;
    public boolean isEmpty = false;

    public Spell() {
        isEmpty = true;
    }

    public Spell(String name, String source, String levelSchool, String metadata, String description, String upcast, String spellList) {
        this.name = name;
        this.source = source;
        this.levelSchool = levelSchool;
        this.metadata = metadata;
        this.description = description;

        this.upcast = upcast;
        if(!this.upcast.isEmpty()) { canUpcastBool = true; }
        if(metadata.contains("ritual")) { ritual = true; }
        if(metadata.contains("concentration")) { ritual = true; }

        this.spellList = spellList;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMetadata() { return  metadata; }

    public String getURL() {
        return URL;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getUpcast() {
        return upcast;
    }

    public String getLevelSchool() { return levelSchool; }

    public String getSpellList() {
        return spellList;
    }

    public boolean isRitual() {
        return ritual;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public boolean canUpcast() {
        return canUpcastBool;
    }

}
