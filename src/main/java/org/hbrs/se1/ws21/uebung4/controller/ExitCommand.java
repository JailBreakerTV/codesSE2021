package org.hbrs.se1.ws21.uebung4.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * This {@link ConsoleCommand} is responsible for gracefully shutting down the application
 */
public final class ExitCommand extends ConsoleCommand {
    public ExitCommand() {
        super("exit", "This command shuts down the program with a delay of 3 seconds");
    }

    @Override
    public void execute(String[] args, CommandParameters parameters) {
        System.out.println("The program will shut down in 3 seconds. Please be patient");
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        };
        timer.schedule(timerTask, TimeUnit.SECONDS.toMillis(3));
    }
}
