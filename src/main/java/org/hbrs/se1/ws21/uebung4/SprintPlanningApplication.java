package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung2.Container;
import org.hbrs.se1.ws21.uebung4.command.*;
import org.hbrs.se1.ws21.uebung4.employee.Employee;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeContainer;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeService;
import org.hbrs.se1.ws21.uebung4.employee.EmployeeServiceImpl;
import org.hbrs.se1.ws21.uebung4.util.ConsoleReader;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinterException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintPlanningApplication {
    public static void main(String[] args) {
        System.out.println(SprintPlanningMessages.STARTUP_MESSAGE);
        final Container<Employee> employeeContainer = EmployeeContainer.getInstance();
        final EmployeeService employeeService = new EmployeeServiceImpl(employeeContainer);
        CommandRegistry.register(new EnterEmployeeCommand(employeeService, employeeContainer));
        CommandRegistry.register(new HelpCommand());
        defaultTablePrint();
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

    private static void defaultTablePrint() {
        final Map<String, List<String>> values = new HashMap<>();
        values.put("EmployeeId", Arrays.asList("1", "2", "3"));
        values.put("Firstname", Arrays.asList("Heinz", "Peter", "Ralf"));
        values.put("LastName", Arrays.asList("Schmitz", "Ulrich", "Horn"));
        values.put("Department", Arrays.asList("Software-Development", "Planning-Team", "Researching"));
        values.put("Expertises", Arrays.asList("Java, Kotlin, MySQL", "Product-Owner", "Journalism"));
        try {
            TablePrinter.printTable(values);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
