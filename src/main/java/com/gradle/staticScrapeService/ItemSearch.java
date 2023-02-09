package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;

public class ItemSearch extends EntitySearch {
    public ItemSearch() {
        super.ENTITY_URL_HREF = "/wondrous-items:";
        super.ENTITY_LIST_URL = "http://dnd5e.wikidot.com/wondrous-items";
        super.entityFactory = new ItemFactory();
        HtmlPage itemListPage = super.gotoPage(ENTITY_LIST_URL);
        super.entityList = getEntityTables(itemListPage, "Item");

    }

}
