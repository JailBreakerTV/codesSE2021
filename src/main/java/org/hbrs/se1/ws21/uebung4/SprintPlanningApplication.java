package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung4.command.*;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeServiceImpl;
import org.hbrs.se1.ws21.uebung4.employee.command.DumpCommand;
import org.hbrs.se1.ws21.uebung4.employee.command.EnterEmployeeCommand;
import org.hbrs.se1.ws21.uebung4.employee.command.LoadCommand;
import org.hbrs.se1.ws21.uebung4.employee.command.StoreCommand;
import org.hbrs.se1.ws21.uebung4.expertise.Expertise;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseContainer;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseService;
import org.hbrs.se1.ws21.uebung4.expertise.ExpertiseServiceImpl;
import org.hbrs.se1.ws21.uebung4.expertise.command.ExpertiseCommand;
import org.hbrs.se1.ws21.uebung4.util.ContinuousConsoleReader;

import java.util.Arrays;

/*
enter -id 1 -firstname Heinz -lastname Schmitz -role Software-Developer -department Software-Engineering-Team -expertise Java -expertise Kotlin -expertise MySQL
enter -id 2 -firstname Peter -lastname Schmidt -role Software-Researcher -department Researching-Team -expertise Kotlin -expertise MySQL
enter -id 0 -firstname Daniel -lastname Peter -role Software-Researcher -department Researching-Team -expertise Kotlin -expertise MySQL
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintPlanningApplication {
    public static void main(String[] args) {
        System.out.println(SprintPlanningMessages.STARTUP_MESSAGE);
        registerCommands();
        handleCommandInputs();
    }

    private static void registerCommands() {
        final EmployeeContainer employeeContainer = EmployeeContainer.getInstance();
        final PersistenceStrategyStream<Employee> employeeStrategy = new PersistenceStrategyStream<>("employees.dat");
        employeeContainer.setStrategy(employeeStrategy);

        final ExpertiseContainer expertiseContainer = ExpertiseContainer.getInstance();
        final PersistenceStrategy<Expertise> expertiseStrategy = new PersistenceStrategyStream<>("expertises.dat");
        expertiseContainer.setStrategy(expertiseStrategy);

        final EmployeeService employeeService = new EmployeeServiceImpl(employeeContainer);
        final ExpertiseService expertiseService = new ExpertiseServiceImpl(expertiseContainer);

        CommandRegistry.register(new ExpertiseCommand(expertiseService, expertiseContainer));
        CommandRegistry.register(new DumpCommand(employeeContainer, expertiseService));
        CommandRegistry.register(new EnterEmployeeCommand(employeeService, employeeContainer, expertiseService));
        CommandRegistry.register(new ExitCommand());
        CommandRegistry.register(new HelpCommand());
        CommandRegistry.register(new ClearCommand());
        CommandRegistry.register(new LoadCommand(employeeContainer));
        CommandRegistry.register(new StoreCommand(employeeContainer));
    }

    private static void handleCommandInputs() {
        ContinuousConsoleReader.start(input -> {
            final String[] arguments = input.split(" ");
            final ConsoleCommand command = CommandRegistry.find(arguments[0]);
            if (command == null) {
                System.out.println("Use 'help' to list all available commands");
                return;
            }
            String[] commandArguments = {};
            if (arguments.length > 1) {
                commandArguments = Arrays.copyOfRange(arguments, 1, arguments.length);
            }
            command.execute(commandArguments, new CommandParameters(commandArguments));
        });
    }
}
