package org.hbrs.se1.ws21.uebung4.command;

import java.util.Collections;

public final class ExitCommand extends ConsoleCommand {
    public ExitCommand() {
        super("exit", "Schaltet das Programm aus", Collections.emptySet());
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("Programm wird ausgeschaltet, bis dann!");
        System.exit(0);
    }
}
