package com.gradle.staticScrapeService;

import java.io.IOException;
import java.util.ArrayList;

public class mainDriver {
    public static String WIKIDOT_URL = "http://dnd5e.wikidot.com";
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
