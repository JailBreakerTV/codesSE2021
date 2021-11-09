package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;

import java.util.Collections;

/**
 * This {@link ConsoleCommand} is responsible for storing all the existing {@link Employee}s to a persistent storage
 */
public final class StoreCommand extends ConsoleCommand {
    /**
     * The {@link EmployeeContainer} which contains all existing {@link Employee}s
     */
    private final EmployeeContainer employeeContainer;

    public StoreCommand(EmployeeContainer employeeContainer) {
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
