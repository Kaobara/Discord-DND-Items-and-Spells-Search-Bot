package com.gradle.staticScrapeService;

public class Item extends  Entity{
    private String typeRarity;
    public Item() {super(); }

    public Item(String name, String source, String typeRarity, String description) {
        super(name, source, description);
        this.typeRarity = typeRarity;
        super.ENTITY_TYPE = "Item";
    }

    public String getTypeRarity() {
        return typeRarity;
    }
}
