package org.hbrs.se1.ws21.uebung4.expertise;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinterException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExpertiseView {
    public static void dump(List<Expertise> expertises) throws TablePrinterException {
        if (expertises.isEmpty()) {
            System.out.println("Die Liste der Expertisen ist leer");
            return;
        }
        final List<String> ids = expertises.stream()
                .map(Expertise::getId)
                .map(Object::toString)
                .collect(Collectors.toList());
        final List<String> titles = expertises.stream()
                .map(Expertise::getTitle)
                .collect(Collectors.toList());
        final Map<String, List<String>> tableContent = new HashMap<>() {{
            put("Id", ids);
            put("Title", titles);
        }};
        TablePrinter.printTable(tableContent);
    }
}
