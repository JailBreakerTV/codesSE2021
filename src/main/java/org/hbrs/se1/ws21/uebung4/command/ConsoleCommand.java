package org.hbrs.se1.ws21.uebung4.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

/**
 * This class represents a command which can be executed by the console
 */
@Getter
@RequiredArgsConstructor
public abstract class ConsoleCommand {
    /**
     * The name of this command which can be used in the console
     */
    private final String name;

    /**
     * The description of this command. This description is useful for the 'help' command
     */
    private final String description;

    /**
     * Possible aliases which can be used instead of the name
     */
    private final Set<String> aliases;

    /**
     * This constructor instantiates a {@link ConsoleCommand} without an alias parameter
     *
     * @param name        of the command
     * @param description of the command
     */
    public ConsoleCommand(String name, String description) {
        this.name = name;
        this.description = description;
        this.aliases = Collections.emptySet();
    }

    /**
     * This function will execute the command with the given input
     *
     * @param args       the executor has passed
     * @param parameters parsed based on the given args
     */
    public abstract void execute(String[] args, CommandParameters parameters);
}
