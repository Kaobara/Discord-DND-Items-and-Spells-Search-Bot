package com.gradle.staticScrapeService;

import java.io.IOException;
import java.util.ArrayList;

public class mainDriver {
    public static String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    public static void main(String[] args) throws IOException{

        EntitySearch service = new EntitySearch();

        SpellSearch spellSearch = new SpellSearch();
        ItemSearch itemSearch = new ItemSearch();
//        Entity entity = spellSearch.searchEntityInfo("fireball");
//        Spell entity = (Spell) spellSearch.searchEntityInfo("symbol");
        Item entity = (Item) itemSearch.searchEntityInfo("Deck of Many Things");
        ArrayList<String> entityDescriptions = entity.getDescriptionSections();
        for(String descriptionSection : entityDescriptions) {
            System.out.println("=======");
            System.out.println(descriptionSection);
        }


//        System.out.println(entity.getName());
//        System.out.println(entity.getSource());
////        System.out.println(entity.getTypeRarity());
//        System.out.println(entity.getDescription());
//        System.out.println(entity.getURL());
    }


}
