package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung4.util.MergingMap;
import org.hbrs.se1.ws21.uebung4.util.NumberConversions;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class CommandParameters {
    private final MergingMap<String> parameters;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CommandParameters(String[] args) {
        this.parameters = this.loadMergedParameters(args);
    }

    private MergingMap<String> loadMergedParameters(String[] args) {
        final MergingMap<String> parameters = new MergingMap<>();
        for (int i = 0; i < args.length; i++) {
            final String key = args[i];
            if (key.startsWith("-")) {
                final int index = i + 1;
                if (index >= args.length) {
                    break;
                }
                final String value = args[index];
                this.lock.writeLock().lock();
                try {
                    parameters.put(key, value);
                } finally {
                    this.lock.writeLock().unlock();
                }
            }
        }
        return parameters;
    }

    public @Nullable Collection<Object> getCollection(String key) {
        final Object value = this.parameters.get(key);
        if (value instanceof Collection) {
            return (Collection<Object>) value;
        }
        return null;
    }

    public @Nullable String getString(String key) {
        final Object obj = this.parameters.get(key);
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    public @Nullable Integer getInteger(String key) {
        return NumberConversions.toInt(this.parameters.get(key));
    }

    public @Nullable Float getFloat(String key) {
        return NumberConversions.toFloat(this.parameters.get(key));
    }

    public @Nullable Double getDouble(String key) {
        return NumberConversions.toDouble(this.parameters.get(key));
    }

    public @Nullable Long getLong(String key) {
        return NumberConversions.toLong(this.parameters.get(key));
    }

    public @Nullable Short getShort(String key) {
        return NumberConversions.toShort(this.parameters.get(key));
    }

    public @Nullable Byte getByte(String key) {
        return NumberConversions.toByte(this.parameters.get(key));
    }
}
