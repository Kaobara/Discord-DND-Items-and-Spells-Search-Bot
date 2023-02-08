package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;

public class SpellSearch extends EntitySearch {
    public SpellSearch() {
        super.ENTITY_URL_HREF = "/spell:";
        super.ENTITY_LIST_URL = "http://dnd5e.wikidot.com/spells";
        super.entityFactory = new SpellFactory();
        HtmlPage spellListPage = super.gotoPage(ENTITY_LIST_URL);
        super.entityList = getEntityTables(spellListPage, "Spell");
    }
}
