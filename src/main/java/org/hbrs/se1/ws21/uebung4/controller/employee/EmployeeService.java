package org.hbrs.se1.ws21.uebung4.controller.employee;

import org.hbrs.se1.ws21.uebung4.model.Employee;
import org.jetbrains.annotations.NotNull;

/**
 * This service interface should handle some basic operations in relation to the {@link Employee} entity
 */
public interface EmployeeService {
    /**
     * This function will generate an random id for the coming {@link Employee}
     *
     * @return Integer representing the id of the next {@link Employee}
     */
    @NotNull Integer generateId();

    /**
     * This function will create a template {@link Employee} with an random generated id
     *
     * @return Employee with an random generated id
     */
    @NotNull Employee create();

    /**
     * This function will create a template {@link Employee} with an given id
     *
     * @param id of the {@link Employee}
     * @return Employee with an specified id
     */
    @NotNull Employee create(Integer id);
}
