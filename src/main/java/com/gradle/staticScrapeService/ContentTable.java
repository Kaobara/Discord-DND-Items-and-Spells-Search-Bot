package com.gradle.staticScrapeService;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import java.util.ArrayList;

public class ContentTable {
    private ArrayList<ArrayList<String>> rows = new ArrayList<>();
    private int numRows, numColumns;

    public ContentTable(HtmlTable table) {
        numRows = table.getRowCount();
        numColumns = 0;

        for (final HtmlTableRow row : table.getRows()) {
            ArrayList<String> currentRow = new ArrayList<>();

            for(final HtmlTableCell cell : row.getCells()) {
                if(row.getIndex() == 0) {
                    numColumns++;
                }
                currentRow.add(cell.getTextContent());
            }
            rows.add(currentRow);
        }

//        int rowIndex = 0;
//        for(ArrayList<String> row : rows) {
//            System.out.println(rowIndex);
//            for(String cell: row) {
//                System.out.println(cell);
//            }
//            rowIndex++;
//        }

    }
}
