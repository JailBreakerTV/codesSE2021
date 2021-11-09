package org.hbrs.se1.ws21.uebung4.test;

import org.hbrs.se1.ws21.uebung4.controller.CommandParameters;
import org.hbrs.se1.ws21.uebung4.controller.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.controller.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.controller.employee.EmployeeServiceImpl;
import org.hbrs.se1.ws21.uebung4.controller.employee.command.EnterEmployeeCommand;
import org.hbrs.se1.ws21.uebung4.controller.expertise.ExpertiseContainer;
import org.hbrs.se1.ws21.uebung4.controller.expertise.ExpertiseService;
import org.hbrs.se1.ws21.uebung4.controller.expertise.ExpertiseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnterEmployeeTest {
    private EmployeeService employeeService;
    private ExpertiseService expertiseService;
    private EmployeeContainer employeeContainer;
    private ExpertiseContainer expertiseContainer;
    private EnterEmployeeCommand enterEmployeeCommand;

    @BeforeEach
    public void init() {
        this.employeeContainer = EmployeeContainer.getInstance();
        this.expertiseContainer = ExpertiseContainer.getInstance();
        this.expertiseService = new ExpertiseServiceImpl(this.expertiseContainer);
        this.employeeService = new EmployeeServiceImpl(this.employeeContainer);
        this.enterEmployeeCommand = new EnterEmployeeCommand(
                this.employeeService,
                this.employeeContainer,
                this.expertiseService
        );
    }

    @Test
    @Order(0)
    public void testEmployeeEnter() {
        /*
         * enter
         *  -id 1
         *  -firstname Heinz
         *  -lastname Schmitz
         *  -role Software-Developer
         *  -department Software-Engineering-Team
         *  -expertise Java
         *  -expertise Kotlin
         *  -expertise MySQL
         */
        final String[] args = {
                "-id", "1",
                "-firstname", "Heinz",
                "-lastname", "Schmitz",
                "-role", "Software-Developer",
                "-department", "Development-Team",
                "-expertise", "Java",
                "-expertise", "Kotlin",
                "-expertise", "MySQL"
        };
        final CommandParameters parameters = new CommandParameters(args);
        this.enterEmployeeCommand.execute(args, parameters);
        assertTrue(this.employeeContainer.hasMember(1));
        assertEquals(1, this.employeeContainer.size());
    }

    @Test
    @Order(1)
    public void testFailEmployeeEnter() {
        assertDoesNotThrow(() -> this.employeeContainer.clear());
        assertEquals(0, this.employeeContainer.size());
        /*
         * enter
         *  -id ABC
         *  -firstname Heinz
         *  -lastname Schmitz
         *  -role Software-Developer
         *  -department Software-Engineering-Team
         *  -expertise Java
         *  -expertise Kotlin
         *  -expertise MySQL
         */
        final String[] args = {
                "-id", "ABC",
                "-firstname", "Heinz",
                "-lastname", "Schmitz",
                "-role", "Software-Developer",
                "-department", "Development-Team",
                "-expertise", "Java"
        };
        final CommandParameters parameters = new CommandParameters(args);
        this.enterEmployeeCommand.execute(args, parameters);
        assertEquals(0, this.employeeContainer.size());
    }
}
