package com.gradle.staticScrapeService;

import java.util.ArrayList;

public abstract class EntityFactory {
    final protected int TOP_I = 0;
    final protected int BOTTOM_I = -1;

    protected String entityListName;

    public abstract Entity createEntity(String entityName, ArrayList<String> fullContent);

    protected abstract Entity createEmptyEntity();

    public String extractSource(ArrayList<String> fullContent) {
        // Split source, levelSchool, metadata, and spellList into separate categories
        return fullContent.remove(TOP_I).replaceFirst("Source: ", "");
    }

    protected String getDescription(ArrayList<String> remainingContent) {
        String description = "";
        for(String content : remainingContent) {
            if(description.isEmpty()) {
                description = content;
            } else {
                if(content.startsWith("-")) { description += "\n" + content;}
                else {description += "\n\n" + content;}
            }
        }
        return description;
    }

}
