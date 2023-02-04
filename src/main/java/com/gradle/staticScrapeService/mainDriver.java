package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class mainDriver {
    public static String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    public static void main(String[] args) throws IOException{

        ScraperService service = new ScraperService();

        SpellSearch spellSearch = new SpellSearch();
        spellSearch.searchSpellInfo("Silvery Barbs");
    }


}
