package org.hbrs.se1.ws21.uebung4.controller.employee.command;

import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung4.controller.CommandParameters;
import org.hbrs.se1.ws21.uebung4.controller.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.model.Employee;
import org.hbrs.se1.ws21.uebung4.controller.employee.EmployeeContainer;

/**
 * This {@link ConsoleCommand} is responsible for storing all the existing {@link Employee}s to a persistent storage
 */
public final class StoreCommand extends ConsoleCommand {
    /**
     * The {@link EmployeeContainer} which contains all existing {@link Employee}s
     */
    private final EmployeeContainer employeeContainer;

    public StoreCommand(EmployeeContainer employeeContainer) {
        super("store", "Saves the employees stored in memory on a persistent structure");
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("The employees will now be persisted");
        try {
            this.employeeContainer.store();
            System.out.println("The employees were successfully persisted");
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
