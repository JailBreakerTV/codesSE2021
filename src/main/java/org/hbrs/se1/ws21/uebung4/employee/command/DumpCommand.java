package org.hbrs.se1.ws21.uebung4.employee.command;

import org.hbrs.se1.ws21.uebung4.command.CommandParameters;
import org.hbrs.se1.ws21.uebung4.command.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeView;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseService;
import org.hbrs.se1.ws21.uebung4.table.TablePrinterException;

import java.util.Collections;
import java.util.List;

/**
 * This {@link ConsoleCommand} implementation will print all existing employees ordered by their ids in a tabe to the console
 *
 * @see ExpertiseService
 * @see EmployeeContainer
 */
public final class DumpCommand extends ConsoleCommand {
    /**
     * The {@link ExpertiseService} which handle some useful operations in relation to {@link Expertise}s
     */
    private final ExpertiseService expertiseService;

    /**
     * The {@link EmployeeContainer} which contains all existing {@link Employee}s
     */
    private final EmployeeContainer employeeContainer;

    public DumpCommand(EmployeeContainer employeeContainer, ExpertiseService expertiseService) {
        super("dump", "This command displays a list of all existing employees");
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
            System.out.println("Currently there are no employees registered in the sprint planning application");
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
