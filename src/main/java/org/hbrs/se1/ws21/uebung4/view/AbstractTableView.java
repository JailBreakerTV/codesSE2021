package org.hbrs.se1.ws21.uebung4.view;

import org.hbrs.se1.ws21.uebung4.view.table.TablePrinterException;

import java.util.Collection;

/**
 * This abstract class provides the structure for displaying a list of items in a table
 *
 * @param <V> which represents an entity which will be displayed in the table
 */
public abstract class AbstractTableView<V> {
    public abstract void dump(Collection<V> collection) throws TablePrinterException;
}
