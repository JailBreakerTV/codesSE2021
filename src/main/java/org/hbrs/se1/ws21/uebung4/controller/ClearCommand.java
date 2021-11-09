package org.hbrs.se1.ws21.uebung4.controller;

public final class ClearCommand extends ConsoleCommand {
    private final String repeat = "\n ".repeat(20);

    public ClearCommand() {
        super("clear", "This command clears the console");
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        for (int i = 0; i < 150; i++) {
            System.out.println(this.repeat);
        }
    }
}
