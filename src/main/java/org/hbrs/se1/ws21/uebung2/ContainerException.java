package org.hbrs.se1.ws21.uebung2;

/**
 * This exception is thrown if an error occurs in container related issues.
 */
public class ContainerException extends Exception {
    /**
     * This constructor instantiates this exception with a given
     * {@link IdAware} instance to throw a predefined exception message
     *
     * @param idAware containing the id for the predefined exception message
     */
    public ContainerException(IdAware idAware) {
        super(String.format("Das Member-Objekt mit der ID %s ist bereits vorhanden!", idAware.getId()));
    }

    /**
     * This constructor instantiates this exception with a given message
     *
     * @param message which should be displayed in the stacktrace
     */
    public ContainerException(String message) {
        super(message);
    }
}
