package org.hbrs.se1.ws21.uebung4.employee;

import org.jetbrains.annotations.NotNull;

public interface EmployeeService {
    @NotNull Integer generateId();

    @NotNull Employee create();

    @NotNull Employee create(Integer id);
}
