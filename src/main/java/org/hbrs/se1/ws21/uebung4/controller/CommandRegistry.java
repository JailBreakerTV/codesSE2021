package org.hbrs.se1.ws21.uebung4.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class is resonsible for managing all {@link ConsoleCommand} implementations
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandRegistry {
    /**
     * The {@link ReadWriteLock} to provide thread-safe operations
     */
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    /**
     * The {@link Map} containing the {@link ConsoleCommand} implementations with their names as key-values
     */
    private static final Map<String, @NotNull ConsoleCommand> COMMAND_MAP = new HashMap<>();

    /**
     * This function will return a snapshot of the currently-existing {@link ConsoleCommand} implementations
     *
     * @return Collection containing all the currently-existing {@link ConsoleCommand} implementations
     */
    public static @NotNull Collection<ConsoleCommand> snapshot() {
        LOCK.readLock().lock();
        try {
            return Collections.unmodifiableCollection(COMMAND_MAP.values());
        } finally {
            LOCK.readLock().unlock();
        }
    }

    /**
     * This function will register a given {@link ConsoleCommand} implementation to be able to
     * use the command in the console
     *
     * @param command which should be registered
     * @throws NullPointerException when the given {@link ConsoleCommand} is null
     */
    public static void register(@Nullable ConsoleCommand command) {
        if (command == null) {
            throw new NullPointerException("ConsoleCommand can not be null");
        }
        LOCK.writeLock().lock();
        try {
            COMMAND_MAP.put(command.getName().toLowerCase(), command);
            System.out.printf("Command '%s' registered%n", command.getName());
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    /**
     * This function will search for a {@link ConsoleCommand} by its name
     *
     * @param name of the {@link ConsoleCommand} which should be searched for
     * @return ConsoleCommand if there exists any or null if there is no command available
     * @throws NullPointerException when the given name is null
     */
    public static @Nullable ConsoleCommand find(@Nullable String name) {
        if (name == null) {
            throw new NullPointerException("Name can not be null");
        }
        LOCK.readLock().lock();
        try {
            return COMMAND_MAP.get(name.toLowerCase());
        } finally {
            LOCK.readLock().unlock();
        }
    }
}
