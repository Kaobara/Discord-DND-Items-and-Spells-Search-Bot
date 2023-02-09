package com.gradle.staticScrapeService;

import java.util.ArrayList;
import java.util.Arrays;

public class Entity {
    final private int MAX_DESCRIPTION_LENGTH = 1023;
    private String name, source, description;
    private String URL;
    private boolean empty = false, hasLongDescription = false;
    private ArrayList<String> descSections = new ArrayList<>();
    private int MAX_DESC_FIELDS = 10;

    public Entity() {
        empty = true;
    }

    public boolean isEmpty() { return empty; };

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

    public ArrayList<String> getDescriptionSections() {
        if(description.length() < MAX_DESCRIPTION_LENGTH) {
            descSections.add(description);
            return descSections;
        }

        hasLongDescription = true;
        String tempString = "";
        String[] descSplit = description.split("\n");
        String currentDescSection = "";
        for(String currentDescLine : descSplit) {
            currentDescSection = tempString;
            tempString += currentDescLine + "\n";
            if(tempString.length() > MAX_DESCRIPTION_LENGTH) {
                descSections.add(currentDescSection);
                tempString = currentDescLine + "\n";
            }
        }

        return descSections;
    }

}
