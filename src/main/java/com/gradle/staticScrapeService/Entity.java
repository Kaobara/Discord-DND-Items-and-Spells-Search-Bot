package com.gradle.staticScrapeService;

import java.util.ArrayList;

public class Entity {
    private String name, source, description;
    private String URL;
    public boolean isEmpty = false, hasLongDescription = false;

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

//    public ArrayList<String> splitDescription() {}

}
