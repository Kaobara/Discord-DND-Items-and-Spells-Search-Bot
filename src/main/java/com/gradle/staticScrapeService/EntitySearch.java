package com.gradle.staticScrapeService;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.sun.scenario.effect.Merge;
import org.apache.commons.text.WordUtils;
import org.w3c.dom.NamedNodeMap;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntitySearch {
    protected final String WIKIDOT_URL = "http://dnd5e.wikidot.com";
    protected String ENTITY_URL_HREF;
    protected String mainContentID = "page-content";
    protected String ENTITY_LIST_URL;
    protected String entityType;
    protected EntityFactory entityFactory;

    protected ArrayList<String> entityList;

    public ArrayList<String> getMainContent(String URL) {
        HtmlPage page = gotoPage(URL);
        return getContentByID(page, mainContentID);
    }


    private static WebClient createWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        return webClient;
    }

    public HtmlPage gotoPage(String URL) {
        WebClient webClient = createWebClient();
        HtmlPage page = null;

        try {
            page = webClient.getPage(URL);
            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
            System.exit(1);
        } catch (FailingHttpStatusCodeException e) {
            // Website sends failing http status exception
            // Possible reasoning: URL does not exist
            System.out.println("An error occurred : " + e);
            System.out.println("Please check that your input is correct");
            System.exit(0);
        }

        return page;
    }

    public ArrayList<String> getContentByID(HtmlPage page, String elementId) {
        DomElement element = page.getElementById(elementId);
        ArrayList<String> textContents = new ArrayList<>();
        DomNodeList<DomNode> nodes = element.querySelectorAll("p, li");
        for(DomNode node : nodes) {
            String paragraphContent = "";
            for(DomNode childNode : node.getChildNodes()) {
                if(childNode.getNodeName().equalsIgnoreCase("br")){
                    continue;
                }
                String formattedText = formatParagraphNodes(childNode.getTextContent(), childNode);
                paragraphContent += formattedText;
            }
            if(node.getNodeName().contains("li")) { paragraphContent = "   - " + paragraphContent; }
            textContents.add(paragraphContent);
        }
        return textContents;
    }

    public ArrayList<String> getEntityTables(HtmlPage page, String entityType) {
        HtmlDivision magicItems = page.getFirstByXPath("//div[@class='yui-navset']");
        List<HtmlTable> magicTables = magicItems.getByXPath("//table[@class='wiki-content-table']");

        ArrayList<String> spellEntityString = new ArrayList<>();

        for(HtmlTable magicTable : magicTables) {
            for (final HtmlTableRow row : magicTable.getRows()) {
                if(row.getCell(0).getTextContent().compareTo(entityType + " Name") != 0) {
                    spellEntityString.add(WordUtils.capitalizeFully(row.getCell(0).getTextContent()));
//                    spellEntityString.add(.capitalize);
                }
            }

        }

        Collections.sort(spellEntityString);
        return  spellEntityString;
    }

    public String formatParagraphNodes(String formattedText, DomNode node) {
        if(node.getNodeName().contains("em")) {
            formattedText = "_" + formattedText + "_";
        } else if(node.getNodeName().contains("strong")) {
            formattedText = "**" + formattedText + "**";
        }
        return formattedText;
    }

    public void getAllLinks(HtmlPage page) {
        List<HtmlAnchor> links = page.getAnchors();
        for (HtmlAnchor link : links) {
            String href = link.getHrefAttribute();
            System.out.println("Link: " + href);
        }
    }

    public Entity searchEntityInfo(String entityName) {
        entityName = entityName.toLowerCase();
        String entityNameHref = entityName.toLowerCase().replace(" ", "-");
        entityName = WordUtils.capitalizeFully(entityName);
        if(!entityList.contains(entityName)){
            System.out.println("HMMMM");
            return entityFactory.createEmptyEntity();
        }
        entityNameHref = entityNameHref.replace("'", "");
        entityNameHref = entityNameHref.replace(":", "");
        String entityURL = WIKIDOT_URL + ENTITY_URL_HREF + entityNameHref;
        System.out.println(entityURL);
        ArrayList<String> entityContent = getMainContent(entityURL);
        for(String string : entityContent) {
            System.out.println(string);
        }

        Entity entity = entityFactory.createEntity(entityName, entityContent);
        entity.setURL(entityURL);


        return entity;
    }

    public EntitySearch() {
    }



}