package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.Locale;

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

    public ArrayList<String> getMainContent(String URL) {
        HtmlPage page = super.gotoPage(URL);
        return super.getContentByID(page, mainContentID);
    }

    public void createSpell(String spellName) {

    }

    public Spell searchSpellInfo(String spellName) {
        spellName = spellName.toLowerCase();
        String spellNameHref = spellName.toLowerCase().replace(" ", "-");
        spellName = WordUtils.capitalizeFully(spellName);
        System.out.println(spellName);
        HtmlPage spellListPage = super.gotoPage("http://dnd5e.wikidot.com/spells");
        ArrayList<String> allSpells = super.getSpellTables(spellListPage);
        if(!allSpells.contains(spellName)){
            return new Spell();
        }
        System.out.println("Spell Exists");
        spellNameHref = spellNameHref.replace("'", "");
        spellNameHref = spellNameHref.replace(":", "");
        String spellURL = WIKIDOT_URL + SPELL_URL_HREF + spellNameHref;
        ArrayList<String> spellContent = getMainContent(spellURL);
        SpellFactory spellFactory = new SpellFactory();
        spell = spellFactory.createSpell(spellName, spellContent);
        spell.setURL(spellURL);

        return spell;
    }
}
