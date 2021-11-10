package org.hbrs.se1.ws21.uebung4.controller.employee.command;

import org.hbrs.se1.ws21.uebung4.controller.CommandParameters;
import org.hbrs.se1.ws21.uebung4.controller.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.controller.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.model.Employee;
import org.hbrs.se1.ws21.uebung4.view.EmployeeView;
import org.hbrs.se1.ws21.uebung4.view.table.TablePrinterException;

import java.util.List;

public class SearchCommand extends ConsoleCommand {
    private final EmployeeContainer employeeContainer;

    public SearchCommand(EmployeeContainer employeeContainer) {
        super("search", "Searches for employees with specific expertises");
        this.employeeContainer = employeeContainer;
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        if (args.length != 1) {
            System.out.println("Use 'search <Expertise>' to list all employees with a specfific expertise");
            return;
        }
        final String expertise = args[0];
        final List<Employee> employees = this.employeeContainer.getCurrentList();
        final List<Employee> filteredEmployees = employees.stream()
                .filter(employee -> employee.getExpertises().stream().anyMatch(exp -> exp.getTitle().equals(expertise)))
                .toList();
        try {
            EmployeeView.getInstance().dump(filteredEmployees);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
