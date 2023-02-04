package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SpellSearch extends ScraperService {
    // Need to find some functionality to try and find a specific spell
    // It take a string
    // and outputs the text

    String SPELL_URL_HREF = "/spell:";
    String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    String mainContentID = "page-content";

    public SpellSearch() {
    }

    public void getMainContent(String URL) {
        HtmlPage page = super.gotoPage(URL);
        super.getContent(page, mainContentID);
    }


    public void searchSpellInfo(String spellName) {
        spellName = spellName.toLowerCase();
        spellName = spellName.replace(" ", "-");
        String spellURL = WIKIDOT_URL + SPELL_URL_HREF + spellName;
        getMainContent(spellURL);
    }
}
