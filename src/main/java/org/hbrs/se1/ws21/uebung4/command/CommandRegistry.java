package org.hbrs.se1.ws21.uebung4.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
            System.err.println("[BEFEHLE] Der zu registrierende Befehl hat keine Referenz im System");
            return;
        }
        LOCK.writeLock().lock();
        try {
            COMMAND_MAP.put(command.getName().toLowerCase(), command);
            System.out.printf("[BEFEHLE] Der Befehl '%s' wurde erfolgreich registriert%n", command.getName());
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
