package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;

public class ContentTable {
    final private ArrayList<ArrayList<String>> rowContents = new ArrayList<>();
    final private ArrayList<ArrayList<String>> headers = new ArrayList<>();
    final private ArrayList<String> finalTableContent = new ArrayList<>();
    final private String finalTable;
    private boolean tableInitialized = false;
    private final int numRows;
    private int numColumns =1;
    private final int FINAL_CELL_SIZE = 20;

    public ContentTable(HtmlTable table) {
        numRows = table.getRowCount();

        boolean firstLoop = true;

        //
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
                // Count the number of columns of the table by only the first row
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
        StringBuilder finalTable = new StringBuilder("```");
        for(ArrayList<String> header : headers) {
            System.out.println("Header Line");
            finalTableContent.add(formatRow(header));
        }

        finalTableContent.add(addDivider());

        for(ArrayList<String> rows : rowContents) {
            finalTableContent.add(formatRow(rows));
        }

        for(String row : finalTableContent) {
            finalTable.append(row).append("\n");
        }

        tableInitialized = true;
        return finalTable + "```";
    }

    private String formatRow(ArrayList<String> row) {
        StringBuilder finalRow = new StringBuilder("| ");
        for(String cell : row ) {
            if(cell.isEmpty()) {
                continue;
            }

            finalRow.append(cell);
            for(int i = cell.length(); i<FINAL_CELL_SIZE; i++) {
                finalRow.append(" ");
            }

            finalRow.append("|");
        }

        return finalRow.toString();
    }

    private String addDivider() {
        StringBuilder divider = new StringBuilder("| ");
        for(int i = 1; i<numColumns; i++) {
            for(int j = 0; j<FINAL_CELL_SIZE; j++) {
                divider.append("-");
            }
            divider.append("+");
        }
        return divider.toString();

    }

    public String getFullTable() {
        if (!tableInitialized) {
            return "";
        }
        return finalTable;
    }


}
