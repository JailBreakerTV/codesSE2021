package org.hbrs.se1.ws21.uebung4.controller;

import org.hbrs.se1.ws21.uebung4.view.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.view.table.TablePrinterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        final List<String> descriptions = snapshot.stream().map(ConsoleCommand::getDescription).collect(Collectors.toList());
        final Map<String, List<String>> columnsAndRows = new HashMap<>();
        columnsAndRows.put("Command", names);
        columnsAndRows.put("Description", descriptions);
        try {
            TablePrinter.printTable(columnsAndRows);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
