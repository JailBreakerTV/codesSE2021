package org.hbrs.se1.ws21.uebung3.persistence;

import java.util.List;

/**
 * Interface for defining basic methods for a persistence mechanism
 * Each concrete algorithm (i.e. strategy) must implement this method
 * This interface corresponds to the abstract strategy w.r.t. to the
 * Strategy Design Pattern (GoF).
 * <p>
 * The following protocol applies:
 * 1. openConnection
 * 2. { load | save }  (many times)
 * 3. closeConnection
 *
 * @param <E>
 */
public interface PersistenceStrategy<E> {
    /**
     * Method for opening the connection to a stream (here: Input- and Output-Stream)
     * In case of having problems while opening the streams, leave the code in methods load
     * and save
     */
    void openConnection() throws PersistenceException;

    /**
     * Method for closing the connections to a stream
     */
    void closeConnection() throws PersistenceException;

    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    void save(List<E> member) throws PersistenceException;

    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    List<E> load() throws PersistenceException;
}
