package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemSearch extends ScraperService {
    // Need to find some functionality to try and find a specific spell
    // It take a string
    // and outputs the text

    private final String SPELL_URL_HREF = "/spell:";
    private final String mainContentID = "page-content";
    private final String SPELL_LIST_URL = "http://dnd5e.wikidot.com/spells";

    private static ArrayList<String> spellList;

    private Spell spell;

    public ItemSearch() {
        HtmlPage spellListPage = super.gotoPage(SPELL_LIST_URL);
        spellList = getSpellTables(spellListPage);
    }

    public ArrayList<String> getMainContent(String URL) {
        HtmlPage page = super.gotoPage(URL);
        return super.getContentByID(page, mainContentID);
    }

    public Spell searchSpellInfo(String spellName) {
        spellName = spellName.toLowerCase();
        String spellNameHref = spellName.toLowerCase().replace(" ", "-");
        spellName = WordUtils.capitalizeFully(spellName);
        if(!spellList.contains(spellName)){
            return new Spell();
        }
        spellNameHref = spellNameHref.replace("'", "");
        spellNameHref = spellNameHref.replace(":", "");
        String spellURL = WIKIDOT_URL + SPELL_URL_HREF + spellNameHref;
        ArrayList<String> spellContent = getMainContent(spellURL);
        SpellFactory spellFactory = new SpellFactory();
        spell = spellFactory.createSpell(spellName, spellContent);
        spell.setURL(spellURL);

        return spell;
    }

    public ArrayList<String> getSpellTables(HtmlPage page) {
        HtmlDivision magicItems = page.getFirstByXPath("//div[@class='yui-navset']");
        List<HtmlTable> magicTables = magicItems.getByXPath("//table[@class='wiki-content-table']");

        ArrayList<String> spellListString = new ArrayList<>();

        for(HtmlTable magicTable : magicTables) {
            for (final HtmlTableRow row : magicTable.getRows()) {
                if(row.getCell(0).getTextContent().compareTo("Spell Name") != 0) {
                    spellListString.add(row.getCell(0).getTextContent());
                }
            }

        }

        Collections.sort(spellListString);
        return  spellListString;
    }
}
