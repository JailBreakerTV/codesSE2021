package org.hbrs.se1.ws21.uebung4.table;

/**
 * This {@link Exception} is thrown when an error occurs while printing the table
 */
public class TablePrinterException extends Exception {
    public TablePrinterException(String message) {
        super(message);
    }
}
