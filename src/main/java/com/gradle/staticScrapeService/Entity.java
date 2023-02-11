package com.gradle.staticScrapeService;

import java.util.ArrayList;

public class Entity {
    final private int MAX_DESCRIPTION_LENGTH = 1023;
    private String name, source, description;
    private String URL;
    private boolean empty = false, longDescription = false, hasTable = false;
    private ArrayList<ContentTable> tables = new ArrayList<>();
    private final ArrayList<String> descSections = new ArrayList<>();

    public Entity() {
        empty = true;
    }

    public boolean isEmpty() { return empty; }

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

    public void buildDescriptionSections() {
        if(description.length() < MAX_DESCRIPTION_LENGTH) {
            descSections.add(description);
            return;
        } else if (longDescription) { return; }

        longDescription = true;
        String tempString = "";
        String[] descSplit = description.split("\n");
        String currentDescSection;
        for(String currentDescLine : descSplit) {
            currentDescSection = tempString;
            tempString += currentDescLine + "\n";
            if(tempString.length() > MAX_DESCRIPTION_LENGTH) {
                descSections.add(currentDescSection);
                tempString = currentDescLine + "\n";
            }
        }
    }

    public void addTablesIntoEntity(ArrayList<ContentTable> tables) {
        hasTable = true;
        this.tables = tables;
    }
    public String getTableContent(int tableIndex){
        if(!hasTable) {
            return "";
        }
        return tables.get(tableIndex).getFullTable();
    }

    public ArrayList<ContentTable> getTables() {
        if(!hasTable) {
            return null;
        }
        return tables;
    }

    public boolean isHasTable() {
        return hasTable;
    }

    public ArrayList<String> getDescSections() { return descSections; }
}
