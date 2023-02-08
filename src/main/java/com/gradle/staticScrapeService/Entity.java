package com.gradle.staticScrapeService;

public class Entity {
    private String name, source, description;
    private String URL;
    public boolean isEmpty = false;

    public Entity() {
        isEmpty = true;
    }

    public Entity(String name, String source,String description) {
        this.name = name;
        this.source = source;
        this.description = description;

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

    public String getDescription() {
        return description;
    }
}
