package org.hbrs.se1.ws21.uebung2;

public class ContainerException extends Exception {
    public ContainerException(Member member) {
        super(String.format("Das Member-Objekt mit der ID %s ist bereits vorhanden!", member.getID()));
    }

    public ContainerException(String message) {
        super(message);
    }
}
