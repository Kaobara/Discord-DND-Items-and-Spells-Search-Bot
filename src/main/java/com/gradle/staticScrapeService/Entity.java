package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlTable;

import java.util.ArrayList;

public class Entity {
    final private int MAX_DESCRIPTION_LENGTH = 1023;
    private String name, source, description;
    private String URL;
    private boolean empty = false, longDescription = false, hasTable = false;
//    private ContentTable contentTable;
    private ArrayList<ContentTable> tables = new ArrayList<>();
    private ArrayList<String> descSections = new ArrayList<>();
    private int MAX_DESC_FIELDS = 10;
    protected String ENTITY_TYPE;

    public String getENTITY_TYPE() {return ENTITY_TYPE;}

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

    public Boolean hasLongDescription() { return longDescription; }

    public void buildDescriptionSections() {
        if(description.length() < MAX_DESCRIPTION_LENGTH) {
            descSections.add(description);
            return;
        } else if (longDescription == true) { return; }

        longDescription = true;
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
    }

    public void addTablesIntoEntity(ArrayList<ContentTable> tables) {
        hasTable = true;
//        for(HtmlTable table : tables) {
//            addTable(table);
//        }
        this.tables = tables;
    }

    private void addTable (HtmlTable table) {
        hasTable = true;
        tables.add(new ContentTable(table));
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
