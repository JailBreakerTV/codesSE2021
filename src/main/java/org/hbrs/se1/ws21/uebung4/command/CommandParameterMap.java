package org.hbrs.se1.ws21.uebung4.command;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CommandParameterMap {
    private final ConsoleCommand command;
    private final Map<String, String> parameters;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CommandParameterMap(ConsoleCommand command, String[] args) {
        this.command = command;
        this.parameters = this.buildMap(args);
    }

    private Map<String, String> buildMap(String[] args) {
        final Map<String, String> parameters = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            final String value = args[i];
            if (value.startsWith("-")) {
                final int nextIdx = i + 1;
                if (nextIdx < args.length) {
                    final String next = args[nextIdx];
                    if (parameters.containsKey(value)) {
                        final String stored = parameters.get(value);
                        parameters.put(value, stored + ";" + next);
                    } else {
                        parameters.put(value, next);
                    }
                }
            }
        }
        return parameters;
    }

    public boolean has(String key) {
        this.lock.readLock().lock();
        try {
            return this.parameters.containsKey(key);
        } finally {
            this.lock.readLock().unlock();
        }
    }

    public @Nullable String getString(String key) {
        if (!this.has(key)) {
            return null;
        }
        return this.parameters.get(key);
    }

    public List<String> getStringList(String key) {
        if (!this.has(key)) {
            return Collections.emptyList();
        }
        return Arrays.stream(this.parameters.get(key).split(";")).toList();
    }
}
