package com.gradle.staticScrapeService;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class webscrapper {
    private static WebClient createWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        return webClient;
    }

    public static void writeCsvFile(String link, String price) throws IOException {
        FileWriter recipesFile = new FileWriter("export.csv", true);
        recipesFile.write("link, price\n");
        recipesFile.write(link + ", " + price);
        recipesFile.close();
    }

    public static void getMainContent(HtmlPage page) {
//        List<DomElement> elements = page.getElementsById("page-content");
//        List<DomElement> elements = page.getByXPath("//div[@class='feature']");
//        List<HtmlTable> elements = page.getElementsByTagName()
//        for(DomElement element : elements) {
//            System.out.println("dot");
//            System.out.println(element.getTextContent());
//        }

//        HtmlTable table = (HtmlTable) page.getFirstByXPath("//table[@class='wiki-content-table']");
//        List<HtmlTable>  tables = page.getByXPath("//table[@class='wiki-content-table']");
        HtmlDivision magicItems = page.getFirstByXPath("//div[@class='yui-navset']");
        List<HtmlTable> tables = magicItems.getByXPath("//table[@class='wiki-content-table']");
//        for(DomElement magicItem : magicItems) {
//            System.out.println(magicItem.getTextContent());
//            System.out.println("=================================================");
//            List<HtmlTable> tables = magicItem.getByXPath();
//
//        }

//        List<HtmlTable>  tables = magicItems.getByXPath("//table[@class='wiki-content-table']");
//        List<HtmlTableBody> tableBodies = tables.getBodies();
//
        for(HtmlTable table : tables) {
            System.out.println("========================================================");
            System.out.println(table.getNodeName());
//            System.out.println(table.getTextContent());
            for(HtmlTableBody tableBody : table.getBodies()) {
                System.out.println(tableBody.getTextContent());
            }
        }

    }


    public static void main(String[] args) throws IOException{
//        FileWriter recipesFile = new FileWriter("recipes.csv", true);
//        recipesFile.write("id,name,link\n");

        WebClient webClient = createWebClient();
        HtmlPage page = null;

        try {
            page = webClient.getPage("http://dnd5e.wikidot.com/wondrous-items");

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();
//            recipesFile.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }

//          List<DomElement> staticEelements = page.getElementsById("page-content");
//        for( DomElement element : staticEelements) {
//            String textcontent = element.getTextContent();
//            System.out.println(textcontent);
//        }

        // Figure out to decouple and make main into a different class from the webscraper
        getMainContent(page);

        List<HtmlAnchor> links = page.getAnchors();
        for (HtmlAnchor link : links) {
            String href = link.getHrefAttribute();
            System.out.println("Link: " + href);
        }
    }
}
