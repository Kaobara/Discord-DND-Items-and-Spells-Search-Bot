package com.gradle.staticScrapeService;

import java.io.IOException;

public class backendSearchTester {
    public static void main(String[] args) throws IOException{

        EntitySearch service = new EntitySearch();

        SpellSearch spellSearch = new SpellSearch();
        ItemSearch itemSearch = new ItemSearch();
//        Item entity = (Item) itemSearch.searchEntityInfo("Potion of Healing");
        Spell entity = (Spell) spellSearch.searchEntityInfo("Wish");
        entity.buildDescriptionSections();

        for(String desct : entity.getDescSections()) {
            System.out.println(desct);
        }
        if(entity.isHasTable()){
            System.out.println(entity.getTableContent(0));
        }
    }


}
