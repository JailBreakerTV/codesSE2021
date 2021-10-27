package org.hbrs.se1.ws21.uebung4.employee;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeContainer extends Container<Employee> {
    private static final Container<Employee> CONTAINER_INSTANCE = new EmployeeContainer();

    public static Container<Employee> getInstance() {
        return CONTAINER_INSTANCE;
    }
}
