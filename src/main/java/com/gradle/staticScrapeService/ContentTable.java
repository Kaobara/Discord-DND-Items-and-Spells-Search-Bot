package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;

public class ContentTable {
    private ArrayList<ArrayList<String>> rowContents = new ArrayList<>();
    private ArrayList<ArrayList<String>> headers = new ArrayList<>();
    private ArrayList<ArrayList<String>> rows = new ArrayList<>();
    private ArrayList<String> finalTableContent = new ArrayList<>();
    private String finalTable;
    boolean tableInitialized = false;
    private int numRows, numColumns =1;
    private final int FINAL_CELL_SIZE = 20;

    public ContentTable(HtmlTable table) {
        numRows = table.getRowCount();
        List<HtmlTableBody> bodies = table.getBodies();

        boolean firstLoop = true;
        for (final HtmlTableRow row : table.getRows()) {
            // Deal with Headers (might make into its own method
            DomNodeList<DomNode> nodes = row.querySelectorAll("th");
            if(!nodes.isEmpty()) {
                ArrayList<String> currentHeaderRow = new ArrayList<>();
                for (DomNode node : nodes) {
                    currentHeaderRow.add(node.getTextContent());
                }
                headers.add(currentHeaderRow);
                continue;
            }

            // Every other row
            ArrayList<String> currentRow = new ArrayList<>();

            // Cell per row
            for(final HtmlTableCell cell : row.getCells()) {
                if(firstLoop) {
                    numColumns++;
                }
                currentRow.add(cell.getTextContent());
            }
            if(firstLoop) {
                firstLoop = false;
            }
            rowContents.add(currentRow);
        }
        finalTable = makeFormattedTable();
    }

    private String makeFormattedTable() {
        String finalTable = "```";
        for(ArrayList<String> header : headers) {
            System.out.println("Header Line");
            finalTableContent.add(formatRow(header));
        }

        finalTableContent.add(addDivider());

        for(ArrayList<String> rows : rowContents) {
            finalTableContent.add(formatRow(rows));
        }

        for(String row : finalTableContent) {
            finalTable += row + "\n";
        }

        tableInitialized = true;
        return finalTable + "```";
    }

    private String formatRow(ArrayList<String> row) {
        String finalRow = "| ";
        for(String cell : row ) {
            if(cell.isEmpty()) {
                continue;
            }

            finalRow += cell;
            for(int i = cell.length(); i<FINAL_CELL_SIZE; i++) {
                finalRow+= " ";
            }

            finalRow += "|";
        }

        return finalRow;
    }

    private String addDivider() {
        String divider = "| ";
        for(int i = 1; i<numColumns; i++) {
            for(int j = 0; j<FINAL_CELL_SIZE; j++) {
                divider+="-";
            }
            divider+="+";
        }
        return divider;

    }

    public String getFullTable() {
        if (!tableInitialized) {
            return "";
        }
        return finalTable;
    }


}
