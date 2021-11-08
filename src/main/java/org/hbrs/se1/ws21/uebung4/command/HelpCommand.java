package org.hbrs.se1.ws21.uebung4.command;

import lombok.extern.java.Log;

import java.util.Collection;
import java.util.Set;

@Log
public class HelpCommand extends ConsoleCommand {
    public HelpCommand() {
        super("help", "Lists alle Befehle mit ihren Beschreibungen auf", Set.of("hilfe"));
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        final Collection<ConsoleCommand> snapshot = CommandRegistry.snapshot();
        log.info("Befehl | Alias | Beschreibung");
        snapshot.forEach(command -> {
        });
    }
}
