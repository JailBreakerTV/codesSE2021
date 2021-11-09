package org.hbrs.se1.ws21.uebung4.view;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.model.Expertise;
import org.hbrs.se1.ws21.uebung4.view.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.view.table.TablePrinterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is responsible for printing a list of {@link Expertise} instances organized in a table
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExpertiseView extends AbstractTableView<Expertise> {
    private static final ExpertiseView VIEW_INSTANCE = new ExpertiseView();

    public static ExpertiseView getInstance() {
        return VIEW_INSTANCE;
    }

    @Override
    public void dump(Collection<Expertise> expertises) throws TablePrinterException {
        if (expertises.isEmpty()) {
            System.out.println("The list of expertises is empty");
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
