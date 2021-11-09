package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws21.uebung4.employee.Employee;

import java.util.Collections;

public final class LoadCommand extends ConsoleCommand {
    private final Container<Employee> employeeContainer;
    private final PersistenceStrategy<Employee> persistenceStrategy;

    public LoadCommand(Container<Employee> employeeContainer, PersistenceStrategy<Employee> persistenceStrategy) {
        super(
                "load",
                "Lädt die abgespeicherten Mitarbeiter in den Arbeitsspeicher",
                Collections.emptySet()
        );
        this.employeeContainer = employeeContainer;
        this.persistenceStrategy = persistenceStrategy;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("[BEGINN] Laden der Mitarbeiter");
        try {
            this.employeeContainer.setStrategy(this.persistenceStrategy);
            this.employeeContainer.load();
            System.out.println("[ENDE] Mitarbeiter erfolgreich geladen");
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
