package org.hbrs.se1.ws21.uebung4.employee.command;

import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.command.CommandParameters;
import org.hbrs.se1.ws21.uebung4.command.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;

import java.util.Collections;

/**
 * This {@link ConsoleCommand} is responsible for loading the {@link Employee}s from the persistent storage
 */
public final class LoadCommand extends ConsoleCommand {
    /**
     * The {@link EmployeeContainer} which contains all existing {@link Employee}s
     */
    private final EmployeeContainer employeeContainer;

    public LoadCommand(EmployeeContainer employeeContainer) {
        super(
                "load",
                "Lädt die abgespeicherten Mitarbeiter in den Arbeitsspeicher",
                Collections.emptySet()
        );
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("[BEGINN] Laden der Mitarbeiter");
        try {
            this.employeeContainer.load();
            System.out.println("[ENDE] Mitarbeiter erfolgreich geladen");
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
