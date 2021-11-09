package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.employee.Employee;

import java.util.Collections;

public final class StoreCommand extends ConsoleCommand {
    private final Container<Employee> employeeContainer;

    public StoreCommand(Container<Employee> employeeContainer) {
        super(
                "store",
                "Speichert die im Arbeitsspeicher hinterlegten Mitarbeiter auf einer persistenten Struktur",
                Collections.emptySet()
        );
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("[BEGINN] Speichern der Mitarbeiter");
        try {
            this.employeeContainer.store();
            System.out.println("[ENDE] Mitarbeiter erfolgreich gespeichert");
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
