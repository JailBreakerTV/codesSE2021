package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung4.NumberConversions;

import java.util.Collections;
import java.util.List;

public class EnterEmployeeCommand extends ConsoleCommand {
    public EnterEmployeeCommand() {
        super("enter", "add new employees", Collections.emptySet());
    }

    @Override
    public void execute(String[] args, CommandParameterMap parameters) {
        /*
         enter
                -id 1
                -firstname Gökhan
                -lastname Topcu
                -role Software-Engineer
                -department Development-Team
                -expertise Java
                -expertise Kotlin
                -expertise MySQL
                -expertise MongoDB
         */
        final String rawId = parameters.getString("-id");
        final Integer id = NumberConversions.toInt(rawId);
        if (id == null) {
            System.err.println("The given Id must be an integer");
            return;
        }

        final String firstName = parameters.getString("-firstname");
        final String lastname = parameters.getString("-lastname");
        final String role = parameters.getString("-role");
        final String department = parameters.getString("-department");
        final List<String> expertise = parameters.getStringList("-expertise");

        System.out.printf("Id: %s%n", rawId);
        System.out.printf("FirstName: %s%n", firstName);
        System.out.printf("LastName: %s%n", lastname);
        System.out.printf("Role: %s%n", role);
        System.out.printf("Department: %s%n", department);
        System.out.printf("Expertise: %s%n", expertise.toString());
    }
}
