package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung4.command.*;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeServiceImpl;
import org.hbrs.se1.ws21.uebung4.util.ConsoleReader;

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
        final Container<Employee> employeeContainer = EmployeeContainer.getInstance();
        final EmployeeService employeeService = new EmployeeServiceImpl(employeeContainer);
        CommandRegistry.register(new DumpCommand(employeeContainer));
        CommandRegistry.register(new EnterEmployeeCommand(employeeService, employeeContainer));
        CommandRegistry.register(new ExitCommand());
        CommandRegistry.register(new HelpCommand());
        CommandRegistry.register(new LoadCommand(employeeContainer, new PersistenceStrategyStream<>("employees.dat")));
        CommandRegistry.register(new StoreCommand(employeeContainer));
    }

    private static void handleCommandInputs() {
        ConsoleReader.start(input -> {
            final String[] arguments = input.split(" ");
            final ConsoleCommand command = CommandRegistry.find(arguments[0]);
            if (command == null) {
                System.out.println("Nutze 'help' um alle Befehle aufzulisten");
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
