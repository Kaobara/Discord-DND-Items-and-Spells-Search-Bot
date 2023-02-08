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

    private final String ITEM_URL_HREF = "/wondrous-item:";
    private final String mainContentID = "page-content";
    private final String SPELL_LIST_URL = "http://dnd5e.wikidot.com/wondrous-items";

    private static ArrayList<String> spellList;

    private Item item;

    public ItemSearch() {
        HtmlPage spellListPage = super.gotoPage(SPELL_LIST_URL);
        spellList = getSpellTables(spellListPage);
    }

    public ArrayList<String> getMainContent(String URL) {
        HtmlPage page = super.gotoPage(URL);
        return super.getContentByID(page, mainContentID);
    }

    public Item searchItemInfo(String ItemName) {
//        ItemName = ItemName.toLowerCase();
//        String itemNameHref = ItemName.toLowerCase().replace(" ", "-");
//        ItemName = WordUtils.capitalizeFully(ItemName);
//        if(!spellList.contains(ItemName)){
//            return new Item();
//        }
//        itemNameHref = itemNameHref.replace("'", "");
//        itemNameHref = itemNameHref.replace(":", "");
//        String spellURL = WIKIDOT_URL + ITEM_URL_HREF + itemNameHref;
//        ArrayList<String> spellContent = getMainContent(spellURL);
//        SpellFactory spellFactory = new SpellFactory();
//        item = itemFactory.createItem(ItemName, spellContent);
//        spell.setURL(spellURL);

        return new Item();
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
