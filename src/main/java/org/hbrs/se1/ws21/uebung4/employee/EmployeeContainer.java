package org.hbrs.se1.ws21.uebung4.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeContainer extends Container<Employee> {
    private static final EmployeeContainer CONTAINER_INSTANCE = new EmployeeContainer();

    public static EmployeeContainer getInstance() {
        return CONTAINER_INSTANCE;
    }

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
