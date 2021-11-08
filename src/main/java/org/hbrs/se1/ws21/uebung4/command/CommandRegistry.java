package org.hbrs.se1.ws21.uebung4.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Log
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandRegistry {
    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final Map<String, ConsoleCommand> COMMAND_MAP = new HashMap<>();

    public static Collection<ConsoleCommand> snapshot() {
        LOCK.readLock().lock();
        try {
            return Collections.unmodifiableCollection(COMMAND_MAP.values());
        } finally {
            LOCK.readLock().unlock();
        }
    }

    public static void register(@Nullable ConsoleCommand command) {
        if (command == null) {
            log.severe("The given command could not be registered because the content is null");
            return;
        }
        LOCK.writeLock().lock();
        try {
            COMMAND_MAP.put(command.getName().toLowerCase(), command);
            log.info(String.format("The command '%s' was registered", command.getName()));
        } finally {
            LOCK.writeLock().unlock();
        }
    }

    public static @Nullable ConsoleCommand find(@Nullable String name) {
        if (name == null) {
            return null;
        }
        LOCK.readLock().lock();
        try {
            return COMMAND_MAP.get(name.toLowerCase());
        } finally {
            LOCK.readLock().unlock();
        }
    }
}
