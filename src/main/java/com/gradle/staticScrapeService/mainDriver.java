package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;

public class mainDriver {
    public static String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    public static void main(String[] args) throws IOException{

        ScraperService service = new ScraperService();

        SpellSearch spellSearch = new SpellSearch();
//        HtmlPage page = service.gotoPage("http://dnd5e.wikidot.com/spell:fireball");
//        ArrayList<String> itemContent = service.getContentByID(page, "page-content");
//        for(String item: itemContent) {
//            System.out.println(item);
//        }
//        service.co
        spellSearch.searchSpellInfo("fireball");
//        HtmlPage spellListPage = service.gotoPage("http://dnd5e.wikidot.com/spells");
//        service.getSpellTables(spellListPage);
    }


}
