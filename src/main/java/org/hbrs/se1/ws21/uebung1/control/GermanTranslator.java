package org.hbrs.se1.ws21.uebung1.control;

import java.util.ArrayList;
import java.util.List;

public class GermanTranslator implements Translator {
//    Leichtere Variante zur Ermittlung der korrekten Übersetzung
//    private static final Map<Integer, String> GERMAN_MAPPED_TRANSLATIONS = new HashMap<Integer, String>() {{
//        put(1, "eins");
//        put(2, "zwei");
//        put(3, "drei");
//        put(4, "vier");
//        put(5, "fünf");
//        put(6, "sechs");
//        put(7, "sieben");
//        put(8, "acht");
//        put(9, "neun");
//        put(10, "zehn");
//    }};

    private static final List<String> GERMAN_TRANSLATIONS = new ArrayList<String>() {{
        add("eins");
        add("zwei");
        add("drei");
        add("vier");
        add("fünf");
        add("sechs");
        add("sieben");
        add("acht");
        add("neun");
        add("zehn");
    }};
    private static final String ERROR_MESSAGE = "Übersetzung der Zahl %s nicht möglich (%s)";

    public String date = "Okt/2021"; // Default-Wert

    /**
     * Methode zur Übersetzung einer Zahl in eine String-Repraesentation
     */
    public String translateNumber(int number) {
        // [ihr Source Code aus Übung 1-2]
        try {
            return GERMAN_TRANSLATIONS.get(number - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return String.format(ERROR_MESSAGE, number, version);
        }

//        Kürzere Variante
//        return GERMAN_MAPPED_TRANSLATIONS.getOrDefault(number, String.format(ERROR_MESSAGE, number, version));
    }

    /**
     * Objektmethode der Klasse GermanTranslator zur Ausgabe einer Info.
     */
    public void printInfo() {
        System.out.println("GermanTranslator v1.9, erzeugt am " + this.date);
    }

    /**
     * Setzen des Datums, wann der Uebersetzer erzeugt wurde (Format: Monat/Jahr (Beispiel: Okt/2021))
     * Das Datum sollte system-intern gesetzt werden und nicht von externen View-Klassen
     */
    public void setDate(String date) {
        this.date = date;
    }

}
