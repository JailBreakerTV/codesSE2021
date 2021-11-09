package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeView;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseService;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinterException;

import java.util.Collections;
import java.util.List;

public final class DumpCommand extends ConsoleCommand {
    private final ExpertiseService expertiseService;
    private final EmployeeContainer employeeContainer;

    public DumpCommand(EmployeeContainer employeeContainer, ExpertiseService expertiseService) {
        super("dump", "Gibt alle vorhandene Mitarbeiter aus", Collections.singleton("ausgabe"));
        this.employeeContainer = employeeContainer;
        this.expertiseService = expertiseService;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final List<Employee> employees = this.employeeContainer.getFilteredEmployees(
                parameters.getString("-abteilung"),
                this.expertiseService.findExpertiseByTitle(parameters.getString("-expertise")).map(Collections::singletonList).orElse(null)
        );
        if (employees.isEmpty()) {
            System.out.println("Aktuell sind keine Mitarbeiter registriert");
            return;
        }
        Collections.sort(employees);
        try {
            EmployeeView.dump(employees);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
