package org.hbrs.se1.ws21.uebung4.view;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.model.Employee;
import org.hbrs.se1.ws21.uebung4.model.Expertise;
import org.hbrs.se1.ws21.uebung4.view.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.view.table.TablePrinterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hbrs.se1.ws21.uebung4.util.CollectionUtil.joinToString;

/**
 * This class is responsible for printing a list of {@link Employee} instances organized in a table
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeView extends AbstractTableView<Employee> {
    private static final EmployeeView VIEW_INSTANCE = new EmployeeView();

    public static EmployeeView getInstance() {
        return VIEW_INSTANCE;
    }

    @Override
    public void dump(Collection<Employee> employees) throws TablePrinterException {
        if (employees.isEmpty()) {
            System.out.println("The list of employees is empty");
            return;
        }
        final List<String> ids = employees.stream()
                .map(Employee::getId)
                .map(Object::toString)
                .collect(Collectors.toList());
        final List<String> firstNames = employees.stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
        final List<String> lastNames = employees.stream()
                .map(Employee::getLastName)
                .collect(Collectors.toList());
        final List<String> roles = employees.stream()
                .map(Employee::getRole)
                .collect(Collectors.toList());
        final List<String> departments = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toList());
        final List<String> expertises = employees.stream().map(employee -> joinToString(
                employee.getExpertises().stream()
                        .map(Expertise::getTitle)
                        .collect(Collectors.toList()),
                ", ",
                "-"
        )).collect(Collectors.toList());
        final Map<String, List<String>> tableContent = new HashMap<>() {{
            put("Id", ids);
            put("Firstname", firstNames);
            put("Lastname", lastNames);
            put("Role", roles);
            put("Department", departments);
            put("Expertises", expertises);
        }};
        TablePrinter.printTable(tableContent);
    }
}
