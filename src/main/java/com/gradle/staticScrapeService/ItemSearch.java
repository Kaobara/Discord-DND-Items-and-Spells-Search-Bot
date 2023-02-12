package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ItemSearch extends EntitySearch {

//    Sets up the EntitySearch to be Item specific
    public ItemSearch() {
        super.ENTITY_URL_HREF = "/wondrous-items:";
        super.ENTITY_LIST_URL = "http://dnd5e.wikidot.com/wondrous-items";
        super.entityFactory = new ItemFactory();
        HtmlPage itemListPage = super.gotoPage(ENTITY_LIST_URL);
        super.entityList = getListofEntities(itemListPage, "Item");
    }

}
