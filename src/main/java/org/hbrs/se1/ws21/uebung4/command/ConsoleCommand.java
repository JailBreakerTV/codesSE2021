package org.hbrs.se1.ws21.uebung4.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public abstract class ConsoleCommand {
    private final String name;
    private final String description;
    private final Set<String> aliases;

    public abstract void execute(String[] args, CommandParameters parameters);

    public void help() {
    }
}
