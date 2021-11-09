package org.hbrs.se1.ws21.uebung4.command;

import lombok.extern.java.Log;
import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung2.ContainerException;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;

import java.util.Collection;
import java.util.Collections;

@Log
public final class EnterEmployeeCommand extends ConsoleCommand {
    private final EmployeeService employeeService;
    private final Container<Employee> employeeContainer;

    public EnterEmployeeCommand(EmployeeService employeeService, Container<Employee> employeeContainer) {
        super("enter", "Dieser Befehl fügt neue Mitarbeiter hinzu", Collections.emptySet());
        this.employeeService = employeeService;
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final Integer id = parameters.getInteger("-id");
        if (id == null) {
            log.severe("Es wurde keine Id angegeben");
            return;
        }
        final String firstName = parameters.getString("-firstname");
        if (firstName == null) {
            log.severe("Es wurde kein Vorname angegeben");
            return;
        }
        final String lastName = parameters.getString("-lastname");
        if (lastName == null) {
            log.severe("Es wurde kein Nachname angegeben");
            return;
        }
        final String role = parameters.getString("-role");
        if (role == null) {
            log.severe("Es wurde keine Rolle angegeben");
            return;
        }
        final String department = parameters.getString("-department");
        if (department == null) {
            log.severe("Es wurde keine Abteilung angegeben");
            return;
        }
        final Collection<Object> expertises = parameters.getCollection("-expertise");
        if (expertises == null) {
            log.severe("Es wurden keine Expertisen angegeben");
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
            log.info("Der Mitarbeiter mit der Id " + employee.getId() + " wurde hinzugefügt");
        } catch (ContainerException e) {
            e.printStackTrace();
        }
    }
}
