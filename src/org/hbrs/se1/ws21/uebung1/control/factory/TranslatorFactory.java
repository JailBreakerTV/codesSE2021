package org.hbrs.se1.ws21.uebung1.control.factory;

import org.hbrs.se1.ws21.uebung1.control.GermanTranslator;
import org.hbrs.se1.ws21.uebung1.control.Translator;

public class TranslatorFactory {
    public static Translator create() {
        return new GermanTranslator();
    }
}
