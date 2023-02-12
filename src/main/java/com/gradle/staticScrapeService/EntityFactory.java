package com.gradle.staticScrapeService;

import java.util.ArrayList;

public abstract class EntityFactory {
    final protected int TOP_I = 0;
    final protected int BOTTOM_I = -1;

    public abstract Entity createEntity(String entityName, ArrayList<String> fullContent, ArrayList<ContentTable> tables);

    protected abstract Entity createEmptyEntity();

    // Both items and spells contains descriptions. The description was initially made as an array of Strings
    // from EntitySearch and the original web page. getDescription() reformats the entire array into a single string
    protected String getDescription(ArrayList<String> remainingContent) {
        StringBuilder description = new StringBuilder();
        for(String content : remainingContent) {
            if(description.length() == 0) {
                description = new StringBuilder(content);
            } else {
                if(content.startsWith("-")) { description.append("\n").append(content);}
                else {
                    description.append("\n\n").append(content);}
            }
        }
        return description.toString();
    }

}
