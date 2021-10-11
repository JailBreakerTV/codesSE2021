package org.hbrs.se1.ws21.uebung1.test;

import org.hbrs.se1.ws21.uebung1.control.Translator;
import org.hbrs.se1.ws21.uebung1.control.factory.TranslatorFactory;
import org.junit.jupiter.api.Test;

public class GermanTranslatorFirstTest {
    @Test
    public void testFirstEquivalenceClass() {
        final int input = 5;
        final String output = "fünf";
        assert input > 0 && input < 11 : String.format("Der Wert %s ist nicht im Bereich der Äquivalenzklasse", input);
        final Translator translator = TranslatorFactory.create();
        final String translated = translator.translateNumber(input);
        assert translated.equals(output) : String.format("Die Übersetzung für den Wert %s ist nicht korrekt", input);
        System.out.println(translated);
    }
}
