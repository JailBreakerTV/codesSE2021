package org.hbrs.se1.ws21.uebung4.table;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.util.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * This class can print a datastructure in a dynamic sized table
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TablePrinter {
    /**
     * This function is responsible for the final table printing
     *
     * @param columnsAndValues which should be displayed in the table
     * @throws TablePrinterException if the column-values does not have the same size
     */
    public static void printTable(Map<String, List<String>> columnsAndValues) throws TablePrinterException {
        final Map<String, Integer> maxLengthOfColumns = loadMaxLengthOfColumns(columnsAndValues);
        final String title = buildTitle(maxLengthOfColumns);
        final String displayColumn = buildDisplayColumn(maxLengthOfColumns);
        System.out.format(title);
        System.out.format(displayColumn);
        System.out.format(title);
        printRows(columnsAndValues, maxLengthOfColumns);
        System.out.format(title);
    }

    /**
     * This function is responsible for printing all the column values in the correct row-entry format
     *
     * @param columnsAndValues   which should be displayed in the table
     * @param maxLengthOfColumns representing the maximum length a column-value has
     * @throws TablePrinterException if the column-values does not have the same size
     */
    private static void printRows(Map<String, List<String>> columnsAndValues, Map<String, Integer> maxLengthOfColumns) throws TablePrinterException {
        final String rowFormat = buildRowFormat(maxLengthOfColumns);
        final List<String> sortedColumns = CollectionUtil.sorted(maxLengthOfColumns.keySet());
        final int lengthOfRowValues = columnsAndValues.values().stream().findFirst().map(List::size).orElseThrow();
        for (List<String> columnValues : columnsAndValues.values()) {
            if (columnValues.size() != lengthOfRowValues) {
                throw new TablePrinterException("Die Zeilen-Werte der entsprechenden Spalten müssen die selbe Länge haben");
            }
        }
        for (int j = 0; j < lengthOfRowValues; j++) {
            final List<String> toBeFormatted = new ArrayList<>();
            for (String column : sortedColumns) {
                final List<String> columnValues = columnsAndValues.get(column);
                if (columnValues == null || j >= columnValues.size()) {
                    continue;
                }
                toBeFormatted.add(columnValues.get(j));
            }
            System.out.format(rowFormat, toBeFormatted.toArray());
        }
    }

    /**
     * This function is responsible for building the separator between the sections
     *
     * @param maxLengthOfColumns representing the maximum length a column-value has
     * @return String containing the separator/divider
     */
    private static String buildTitle(Map<String, Integer> maxLengthOfColumns) {
        final StringBuilder builder = new StringBuilder();
        final List<String> sortedColumns = CollectionUtil.sorted(maxLengthOfColumns.keySet());
        sortedColumns.forEach(column -> {
            final int length = maxLengthOfColumns.get(column);
            builder.append("+").append("-".repeat(max(0, length + 2)));
        });
        builder.append("+%n");
        return builder.toString();
    }

    /**
     * This function is responsible for building the dynamic row-entry format
     *
     * @param maxLengthOfColumns representing the maximum length a column-value has
     * @return String containing a correct row-entry format
     */
    private static String buildRowFormat(Map<String, Integer> maxLengthOfColumns) {
        final StringBuilder builder = new StringBuilder();
        final List<String> sortedColumns = CollectionUtil.sorted(maxLengthOfColumns.keySet());
        sortedColumns.forEach(column -> {
            final int length = maxLengthOfColumns.get(column);
            builder.append("| %-").append(length).append("s ");
        });
        builder.append("|%n");
        return builder.toString();
    }

    /**
     * This function will build the column display section based on the maximum character-length
     *
     * @param maxLengthOfColumns representing the maximum length a column-value has
     * @return String containing the correct formatted column display section
     */
    private static String buildDisplayColumn(Map<String, Integer> maxLengthOfColumns) {
        final StringBuilder builder = new StringBuilder();
        final List<String> sortedColumns = CollectionUtil.sorted(maxLengthOfColumns.keySet());
        sortedColumns.forEach(column -> {
            final int length = maxLengthOfColumns.get(column);
            builder.append("| ").append(column).append(" ".repeat(abs(length - column.length()) + 1));
        });
        builder.append("|%n");
        return builder.toString();
    }

    /**
     * This function will search for the maximum character-length a value in a column has
     *
     * @param values which should be displayed in the table
     * @return Map with the column names as keys and the maximum character-length as values
     */
    private static Map<String, Integer> loadMaxLengthOfColumns(Map<String, List<String>> values) {
        final Map<String, Integer> maxLengthPerColumn = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            final String key = entry.getKey();
            final List<String> value = entry.getValue();
            final int columnLength = key.length();
            final int maxValueLength = value.stream().mapToInt(String::length).max().orElseThrow();
            maxLengthPerColumn.put(key, Integer.max(columnLength, maxValueLength));
        }
        return maxLengthPerColumn;
    }
}
