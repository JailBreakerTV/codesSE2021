package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung4.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.table.TablePrinterException;

import java.util.*;
import java.util.stream.Collectors;

import static org.hbrs.se1.ws21.uebung4.util.CollectionUtil.joinToString;

/**
 * This {@link ConsoleCommand} is responsible for delivering all the existing {@link ConsoleCommand} to the executor
 */
public final class HelpCommand extends ConsoleCommand {
    public HelpCommand() {
        super("help", "This command lists all existing commands with their descriptions");
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final Collection<ConsoleCommand> snapshot = CommandRegistry.snapshot();
        final List<String> names = snapshot.stream().map(ConsoleCommand::getName).collect(Collectors.toList());
        final List<String> aliases = snapshot.stream().map(command -> joinToString(command.getAliases(), ", ", "-")).collect(Collectors.toList());
        final List<String> descriptions = snapshot.stream().map(ConsoleCommand::getDescription).collect(Collectors.toList());
        final Map<String, List<String>> columnsAndRows = new HashMap<>();
        columnsAndRows.put("Command", names);
        columnsAndRows.put("Aliases", aliases);
        columnsAndRows.put("Description", descriptions);
        try {
            TablePrinter.printTable(columnsAndRows);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
