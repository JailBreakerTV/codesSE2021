package org.hbrs.se1.ws21.uebung4.controller.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung4.model.Employee;
import org.hbrs.se1.ws21.uebung4.model.Expertise;

import java.util.ArrayList;
import java.util.List;

/**
 * This {@link Container} implementation is responsible for handling {@link Employee} instances
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeContainer extends Container<Employee> {
    /**
     * Applying the singleton pattern to the {@link Container} implementation
     */
    private static final EmployeeContainer CONTAINER_INSTANCE = new EmployeeContainer();

    /**
     * This function returns the singleton instance of this {@link Container} implemenation
     *
     * @return EmployeeContainer singleton instance
     */
    public static EmployeeContainer getInstance() {
        return CONTAINER_INSTANCE;
    }

    /**
     * This function will deliver a filtered {@link Employee} list based on the given parameters
     *
     * @param department to which the employees belong
     * @param expertises the employees should have
     * @return List containing the employees who have the given specification
     */
    public List<Employee> getFilteredEmployees(String department, List<Expertise> expertises) {
        final List<Employee> employees = new ArrayList<>(this.getCurrentList());
        if (department != null) {
            employees.removeIf(employee -> employee.getDepartment().equalsIgnoreCase(department));
        }
        if (expertises != null && !expertises.isEmpty()) {
            employees.removeIf(employee -> !expertises.containsAll(employee.getExpertises()));
        }
        return employees;
    }
}
