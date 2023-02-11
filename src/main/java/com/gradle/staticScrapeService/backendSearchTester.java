package com.gradle.staticScrapeService;

import java.io.IOException;

public class backendSearchTester {
    public static void main(String[] args) throws IOException{

        EntitySearch service = new EntitySearch();

        SpellSearch spellSearch = new SpellSearch();
        ItemSearch itemSearch = new ItemSearch();
        Item entity = (Item) itemSearch.searchEntityInfo("Potion of Healing");
        if(entity.isHasTable()){
            System.out.println(entity.getTableContent(0));
        }
    }


}
