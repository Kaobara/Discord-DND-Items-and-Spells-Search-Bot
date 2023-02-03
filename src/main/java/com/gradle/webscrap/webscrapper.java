package com.gradle.webscrap;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;

import java.io.FileWriter;
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


    public static void main(String[] args) throws IOException{
//        FileWriter recipesFile = new FileWriter("recipes.csv", true);
//        recipesFile.write("id,name,link\n");

        WebClient webClient = createWebClient();
        HtmlPage page = null;

        try {
            page = webClient.getPage("https://foodnetwork.co.uk/italian-family-dinners/");

            webClient.getCurrentWindow().getJobManager().removeAllJobs();
            webClient.close();
//            recipesFile.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }

        // extract specific sections
        String title = page.getTitleText();
        System.out.println("Page Title: " + title);
    }
}
