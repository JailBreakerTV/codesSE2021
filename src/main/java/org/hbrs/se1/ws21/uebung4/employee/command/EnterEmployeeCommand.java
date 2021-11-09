package org.hbrs.se1.ws21.uebung4.employee.command;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung4.command.CommandParameters;
import org.hbrs.se1.ws21.uebung4.command.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;

import java.util.Collection;
import java.util.Collections;

/**
 * This {@link ConsoleCommand} is responsible for the creation of new {@link Employee} entries
 */
public final class EnterEmployeeCommand extends ConsoleCommand {
    /**
     * The {@link EmployeeService} which provides useful functions for handling {@link Employee}s
     */
    private final EmployeeService employeeService;

    /**
     * The {@link EmployeeContainer} which contains all existing {@link Employee}s
     */
    private final EmployeeContainer employeeContainer;

    public EnterEmployeeCommand(EmployeeService employeeService, EmployeeContainer employeeContainer) {
        super("enter", "Dieser Befehl fügt neue Mitarbeiter hinzu", Collections.emptySet());
        this.employeeService = employeeService;
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final Integer id = parameters.getInteger("-id");
        if (id == null) {
            System.err.println("Es wurde keine Id angegeben");
            return;
        }
        final String firstName = parameters.getString("-firstname");
        if (firstName == null) {
            System.err.println("Es wurde kein Vorname angegeben");
            return;
        }
        final String lastName = parameters.getString("-lastname");
        if (lastName == null) {
            System.err.println("Es wurde kein Nachname angegeben");
            return;
        }
        final String role = parameters.getString("-role");
        if (role == null) {
            System.err.println("Es wurde keine Rolle angegeben");
            return;
        }
        final String department = parameters.getString("-department");
        if (department == null) {
            System.err.println("Es wurde keine Abteilung angegeben");
            return;
        }
        final Collection<Object> expertises = parameters.getCollection("-expertise");
        if (expertises == null) {
            System.err.println("Es wurden keine Expertisen angegeben");
            return;
        }
        try {
            final Employee employee = this.employeeService.create(id);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setDepartment(department);
            expertises.stream()
                    .map(Object::toString)
                    .map(expertiseName -> new Expertise(0, expertiseName))
                    .forEach(expertise -> employee.getExpertises().add(expertise));
            this.employeeContainer.addMember(employee);
            System.out.printf("Der Mitarbeiter mit der Id %d wurde hinzugefügt%n", employee.getId());
        } catch (ContainerException e) {
            e.printStackTrace();
        }
    }
}
