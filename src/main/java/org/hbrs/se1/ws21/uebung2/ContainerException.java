package org.hbrs.se1.ws21.uebung2;

public class ContainerException extends Exception {
    public ContainerException(IdAware idAware) {
        super(String.format("Das Member-Objekt mit der ID %s ist bereits vorhanden!", idAware.getId()));
    }

    public ContainerException(String message) {
        super(message);
    }
}
