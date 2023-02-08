package com.gradle.staticScrapeService;

import discord4j.core.spec.EmbedCreateFields;

import java.util.Locale;

public class Spell extends Entity {
    private String levelSchool, metadata, upcast, spellList;
    private boolean ritual = false, concentration = false, canUpcastBool = false;

    public Spell() {
        super();
    }

    public Spell(String name, String source, String levelSchool, String metadata, String description, String upcast, String spellList) {
        super(name, source, description);
        this.levelSchool = levelSchool;
        this.metadata = metadata;

        this.upcast = upcast;
        if(!this.upcast.isEmpty()) { canUpcastBool = true; }
        if(metadata.contains("ritual")) { ritual = true; }
        if(metadata.contains("concentration")) { ritual = true; }

        this.spellList = spellList;
    }

    public String getMetadata() { return  metadata; }

    public String getUpcast() {
        return upcast;
    }

    public String getLevelSchool() { return levelSchool; }

    public boolean isRitual() {
        return ritual;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public String getSpellList() {
        return spellList;
    }

    public void setSpellList(String spellList) {this.spellList = spellList; }

    public boolean canUpcast() {
        return canUpcastBool;
    }

}
