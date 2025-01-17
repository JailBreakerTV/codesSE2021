package org.hbrs.se1.ws21.solutions.uebung1.control;

/**
 * Interface fuer Translator-Objekte, muss auf jeden Fall auf public gesetzt werden, sonst nicht sichtbar
 * fuer andere Packages
 *
 * @author saschaalda
 */
public interface Translator {

    double version = 1.0;

    String translateNumber(int number);

} 
 