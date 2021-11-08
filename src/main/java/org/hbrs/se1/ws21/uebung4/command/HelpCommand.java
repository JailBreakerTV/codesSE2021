package org.hbrs.se1.ws21.uebung4.command;

import lombok.extern.java.Log;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinter;
import org.hbrs.se1.ws21.uebung4.util.table.TablePrinterException;

import java.util.*;
import java.util.stream.Collectors;

import static org.hbrs.se1.ws21.uebung4.util.CollectionUtil.joinToString;

@Log
public final class HelpCommand extends ConsoleCommand {
    public HelpCommand() {
        super("help", "Lists alle Befehle mit ihren Beschreibungen auf", Set.of("hilfe"));
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final Collection<ConsoleCommand> snapshot = CommandRegistry.snapshot();
        final List<String> names = snapshot.stream().map(ConsoleCommand::getName).collect(Collectors.toList());
        final List<String> aliases = snapshot.stream().map(command -> joinToString(command.getAliases(), ", ", "-")).collect(Collectors.toList());
        final List<String> descriptions = snapshot.stream().map(ConsoleCommand::getDescription).collect(Collectors.toList());

        final Map<String, List<String>> columnsAndRows = new HashMap<>();
        columnsAndRows.put("Befehl", names);
        columnsAndRows.put("Aliases", aliases);
        columnsAndRows.put("Beschreibung", descriptions);
        try {
            TablePrinter.printTable(columnsAndRows);
        } catch (TablePrinterException e) {
            e.printStackTrace();
        }
    }
}
