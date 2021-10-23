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
     * This function should establish the connection to the persistence interface
     */
    void openConnection() throws PersistenceException;

    /**
     * Method for closing the connections
     */
    void closeConnection() throws PersistenceException;

    /**
     * Method for saving a list of generic objects to a disk (HDD)
     */
    void save(List<E> member) throws PersistenceException;

    /**
     * Method for loading a list of generic objects from a disk (HDD)
     */
    List<E> load() throws PersistenceException;
}
