package com.gradle.staticScrapeService;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.sun.scenario.effect.Merge;
import org.w3c.dom.NamedNodeMap;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScraperService {
    protected final String WIKIDOT_URL = "http://dnd5e.wikidot.com";

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
//        List<DomElement> elements = page.getElementsById(elementId);
        DomElement element = page.getElementById(elementId);
        ArrayList<String> textContents = new ArrayList<>();
//        textContents.add(element.getTextContent());
        DomNodeList<DomNode> nodes = element.querySelectorAll("p, li");
        for(DomNode node : nodes) {
//            System.out.println(node.asXml());
            String paragraphContent = "";
            for(DomNode childNode : node.getChildNodes()) {
                if(childNode.getNodeName().equalsIgnoreCase("br")){
                    continue;
                }

                String formattedText = formatParagraphNodes(childNode.getTextContent(), childNode);

                paragraphContent += formattedText;
            }
            if(node.getNodeName().contains("li")) { paragraphContent = "- " + paragraphContent; }
            textContents.add(paragraphContent);
        }
        return textContents;
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

    public ScraperService() {
    }



}


//public class scraperService {

//
////    private static HtmlPage
//
//    public static void writeCsvFile(String link, String price) throws IOException {
//        FileWriter recipesFile = new FileWriter("export.csv", true);
//        recipesFile.write("link, price\n");
//        recipesFile.write(link + ", " + price);
//        recipesFile.close();
//    }
//
//    public static void getMainContent(HtmlPage page) {
////        List<DomElement> elements = page.getElementsById("page-content");
////        List<DomElement> elements = page.getByXPath("//div[@class='feature']");
////        List<HtmlTable> elements = page.getElementsByTagName()
////        for(DomElement element : elements) {
////            System.out.println("dot");
////            System.out.println(element.getTextContent());
////        }
//
////        HtmlTable table = (HtmlTable) page.getFirstByXPath("//table[@class='wiki-content-table']");
////        List<HtmlTable>  tables = page.getByXPath("//table[@class='wiki-content-table']");
//        HtmlDivision magicItems = page.getFirstByXPath("//div[@class='yui-navset']");
//        List<HtmlTable> tables = magicItems.getByXPath("//table[@class='wiki-content-table']");
////        for(DomElement magicItem : magicItems) {
////            System.out.println(magicItem.getTextContent());
////            System.out.println("=================================================");
////            List<HtmlTable> tables = magicItem.getByXPath();
////
////        }
//
////        List<HtmlTable>  tables = magicItems.getByXPath("//table[@class='wiki-content-table']");
////        List<HtmlTableBody> tableBodies = tables.getBodies();
////
//        for(HtmlTable table : tables) {
//            System.out.println("========================================================");
//            System.out.println(table.getNodeName());
////            System.out.println(table.getTextContent());
//            for(HtmlTableBody tableBody : table.getBodies()) {
//                System.out.println(tableBody.getTextContent());
//            }
//        }
//
//    }
//
//    public scraperService() {
//        WebClient webClient = createWebClient();
//        HtmlPage page = null;
//    }
//
//    public static void main(String[] args) throws IOException{
////        FileWriter recipesFile = new FileWriter("recipes.csv", true);
////        recipesFile.write("id,name,link\n");
//
//        WebClient webClient = createWebClient();
//        HtmlPage page = null;
//
//        try {
//            page = webClient.getPage("http://dnd5e.wikidot.com/wondrous-items");
//
//            webClient.getCurrentWindow().getJobManager().removeAllJobs();
//            webClient.close();
//
//        } catch (IOException e) {
//            System.out.println("An error occurred: " + e);
//        }
//
//        // Figure out to decouple and make main into a different class from the webscraper
//        getMainContent(page);
//
//        List<HtmlAnchor> links = page.getAnchors();
//        for (HtmlAnchor link : links) {
//            String href = link.getHrefAttribute();
//            System.out.println("Link: " + href);
//        }
//    }
//}