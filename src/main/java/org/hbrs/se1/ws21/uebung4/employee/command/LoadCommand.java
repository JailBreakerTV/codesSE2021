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
        super("load", "Loads the saved employees into the memory");
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("The employees are loaded from the persistence store");
        try {
            this.employeeContainer.load();
            System.out.println("The employees were successfully loaded from the persistence store");
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
