package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hbrs.se1.ws21.uebung4.command.CommandParameterMap;
import org.hbrs.se1.ws21.uebung4.command.CommandRegistry;
import org.hbrs.se1.ws21.uebung4.command.ConsoleCommand;
import org.hbrs.se1.ws21.uebung4.command.EnterEmployeeCommand;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintPlanningApplication {
    public static void main(String[] args) {
        System.out.println(SprintPlanningMessages.STARTUP_MESSAGE);
        CommandRegistry.register(new EnterEmployeeCommand());
        ConsoleReader.read(input -> {
            final String[] rawArguments = input.split(" ");
            final ConsoleCommand command = CommandRegistry.find(rawArguments[0]);
            if (command == null) {
                System.out.println("Use the command 'help' to show a list of all commands");
                return;
            }
            String[] commandArguments = {};
            if (rawArguments.length > 1) {
                commandArguments = Arrays.copyOfRange(rawArguments, 1, rawArguments.length);
            }
            final CommandParameterMap parameters = new CommandParameterMap(command, commandArguments);
            command.execute(commandArguments, parameters);
        });
    }
}
