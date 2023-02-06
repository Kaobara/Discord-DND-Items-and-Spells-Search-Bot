package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.ArrayList;

public class SpellSearch extends ScraperService {
    // Need to find some functionality to try and find a specific spell
    // It take a string
    // and outputs the text

    private final String SPELL_URL_HREF = "/spell:";
    private final String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    private final String mainContentID = "page-content";

    private Spell spell;

    public SpellSearch() {
    }

    public String getMainContent(String URL) {
        HtmlPage page = super.gotoPage(URL);
        return super.getContentByID(page, mainContentID).get(0);
    }

    public void createSpell(String spellName) {

    }

    public Spell searchSpellInfo(String spellName) {
        String spellNameHref = spellName.toLowerCase().replace(" ", "-");
        String spellURL = WIKIDOT_URL + SPELL_URL_HREF + spellNameHref;
        String spellContent = getMainContent(spellURL);
        SpellFactory spellFactory = new SpellFactory();
        spell = spellFactory.createSpell(spellName, spellContent);
        spell.setURL(spellURL);

        return spell;
    }
}
