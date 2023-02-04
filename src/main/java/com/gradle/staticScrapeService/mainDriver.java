package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class mainDriver {
    public static String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    public static void main(String[] args) throws IOException{

        ScraperService service = new ScraperService();

//        HtmlPage page = service.gotoPage("http://dnd5e.wikidot.com/spell:acid-splash");
//        service.getContent(page, "page-content");
        SpellSearch spellSearch = new SpellSearch();
        spellSearch.searchSpellInfo("Silvery Barbs");
//        service.gotoPage("http://dnd5e.wikidot.com/spell:acid-splash");
    }
}
