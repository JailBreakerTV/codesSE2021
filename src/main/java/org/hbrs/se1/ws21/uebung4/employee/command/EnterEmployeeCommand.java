package org.hbrs.se1.ws21.uebung4.employee.command;

import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung4.command.CommandParameters;
import org.hbrs.se1.ws21.uebung4.command.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseService;
import org.hbrs.se1.ws21.uebung4.util.StringUtil;

import java.util.Collection;
import java.util.Optional;

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

    /**
     * The {@link ExpertiseService} which handle some useful operations in relation to {@link Expertise}s
     */
    private final ExpertiseService expertiseService;

    public EnterEmployeeCommand(EmployeeService employeeService, EmployeeContainer employeeContainer, ExpertiseService expertiseService) {
        super("enter", "This command adds a new employee");
        this.employeeService = employeeService;
        this.employeeContainer = employeeContainer;
        this.expertiseService = expertiseService;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final Integer id = parameters.getInteger("-id");
        if (id == null) {
            System.err.println("No Id was specified or the specified Id is not an integer");
            System.out.println("Please use -id <Id> to specify an id as an integer");
            return;
        }
        final String firstName = parameters.getString("-firstname");
        if (firstName == null || StringUtil.isNumeric(firstName)) {
            System.err.println("No first name was specified, or the specified first name does not match a valid string");
            System.out.println("Please use -firstname <Firstname> to specify the employees first name");
            return;
        }
        final String lastName = parameters.getString("-lastname");
        if (lastName == null || StringUtil.isNumeric(lastName)) {
            System.err.println("No last name was specified, or the specified last name does not match a valid string");
            System.out.println("Please use -lastname <Lastname> to specify the employees last name");
            return;
        }
        final String role = parameters.getString("-role");
        if (role == null || StringUtil.isNumeric(role)) {
            System.err.println("No role was specified, or the specified role does not match a valid string");
            System.out.println("Please use -role <Role> to specify the employees role");
            return;
        }
        final String department = parameters.getString("-department");
        if (department == null || StringUtil.isNumeric(department)) {
            System.err.println("No department was specified, or the specified department does not match a valid string");
            System.out.println("Please use -department <Department> to specify the employees department");
            return;
        }
        final Collection<Object> expertises = parameters.getCollection("-expertise");
        if (expertises == null) {
            System.err.println("No expertises are specified");
            System.out.println("Please use -expertise <Expertise> to specify the employees expertises");
            return;
        }
        try {
            final Employee employee = this.employeeService.create(id);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setRole(role);
            employee.setDepartment(department);
            expertises.stream()
                    .map(Object::toString)
                    .map(this.expertiseService::findExpertiseByTitle)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(expertise -> employee.getExpertises().add(expertise));
            this.employeeContainer.addMember(employee);
            System.out.printf("Der Mitarbeiter mit der Id %d wurde hinzugefügt%n", employee.getId());
        } catch (ContainerException e) {
            e.printStackTrace();
        }
    }
}
