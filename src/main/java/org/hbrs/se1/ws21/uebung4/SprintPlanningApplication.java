package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws21.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws21.uebung4.command.*;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeServiceImpl;
import org.hbrs.se1.ws21.uebung4.util.ConsoleReader;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintPlanningApplication {
    public static void main(String[] args) {
        System.out.println(SprintPlanningMessages.STARTUP_MESSAGE);
        registerCommands();
        handleCommandInputs();
        registerShutdownHook();
    }

    private static void registerCommands() {
        final Container<Employee> employeeContainer = EmployeeContainer.getInstance();
        final PersistenceStrategy<Employee> strategy = new PersistenceStrategyStream<>("employees.dat");
        try {
            strategy.openConnection();
            employeeContainer.setStrategy(strategy);
            employeeContainer.load();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        final EmployeeService employeeService = new EmployeeServiceImpl(employeeContainer);
        CommandRegistry.register(new HelpCommand());
        CommandRegistry.register(new ExitCommand());
        CommandRegistry.register(new DumpCommand(employeeContainer));
        CommandRegistry.register(new EnterEmployeeCommand(employeeService, employeeContainer));
    }

    /*
    enter -id 1 -firstname Heinz -lastname Schmitz -role Software-Developer -department Software-Engineering-Team -expertise Java -expertise Kotlin -expertise MySQL
    enter -id 2 -firstname Peter -lastname Schmidt -role Software-Researcher -department Researching-Team -expertise Kotlin -expertise MySQL
     */

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

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                EmployeeContainer.getInstance().store();
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }));
    }
}
