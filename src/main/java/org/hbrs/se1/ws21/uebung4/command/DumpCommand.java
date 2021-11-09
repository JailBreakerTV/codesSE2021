package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinterException;

import java.util.*;
import java.util.stream.Collectors;

import static org.hbrs.se1.ws21.uebung4.util.CollectionUtil.joinToString;

public final class DumpCommand extends ConsoleCommand {
    private final Container<Employee> employeeContainer;

    public DumpCommand(Container<Employee> employeeContainer) {
        super("dump", "Gibt alle vorhandene Mitarbeiter aus", Collections.singleton("ausgabe"));
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final List<Employee> employees = new ArrayList<>(this.employeeContainer.getCurrentList());
        if (employees.isEmpty()) {
            System.out.println("Aktuell sind keine Mitarbeiter registriert");
            return;
        }
        Collections.sort(employees);
        final List<String> ids = employees.stream().map(Employee::getId).map(Object::toString).collect(Collectors.toList());
        final List<String> firstNames = employees.stream().map(Employee::getFirstName).collect(Collectors.toList());
        final List<String> lastNames = employees.stream().map(Employee::getLastName).collect(Collectors.toList());
        final List<String> departments = employees.stream().map(Employee::getDepartment).collect(Collectors.toList());
        final List<String> expertises = employees.stream().map(employee ->
                joinToString(
                        employee.getExpertises().stream().map(Expertise::getTitle).collect(Collectors.toList()),
                        ", ",
                        "-"
                )
        ).collect(Collectors.toList());

        final Map<String, List<String>> tableContent = new HashMap<>() {{
            put("Id", ids);
            put("Firstname", firstNames);
            put("Lastname", lastNames);
            put("Department", departments);
            put("Expertises", expertises);
        }};
        try {
            TablePrinter.printTable(tableContent);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
