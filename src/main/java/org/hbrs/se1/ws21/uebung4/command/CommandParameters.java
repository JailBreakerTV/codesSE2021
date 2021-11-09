package org.hbrs.se1.ws21.uebung4.command;

import org.hbrs.se1.ws21.uebung4.util.MergingMap;
import org.hbrs.se1.ws21.uebung4.util.NumberConversions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class will contain all command-parameters mapped in a {@link MergingMap}
 */
public final class CommandParameters {
    /**
     * {@link MergingMap} for handling all command-parameters
     */
    private final MergingMap<String> parameters;

    /**
     * The {@link ReadWriteLock} to provide thread-safe operations
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Instantiates this class
     *
     * @param args which come from the console-input
     */
    public CommandParameters(String[] args) {
        this.parameters = this.loadMergedParameters(args);
    }

    /**
     * This function will filter out all command-parameters from the given console-input
     *
     * @param args which come from the console-input
     * @return MergingMap containing all the given command-parameters
     */
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

    /**
     * This function specifies whether there are parameters for the specified key or not
     *
     * @param key of the parameter
     * @return true if there are some parameters for this key or false if not so
     */
    public boolean has(@NotNull String key) {
        return this.parameters.containsKey(key);
    }

    /**
     * This function gets the current value for the given key and casts it to a {@link Collection}
     *
     * @param key of the parameter
     * @return Collection containing all parameters for the same key or
     * null if the value is not a subtype of {@link Collection}
     */
    public @Nullable Collection<Object> getCollection(String key) {
        final Object value = this.parameters.get(key);
        if (value instanceof Collection) {
            return (Collection<Object>) value;
        }
        return null;
    }

    /**
     * This function will return the value for the given key as a String
     *
     * @param key of the parameter
     * @return String containing the value or null if there is no parameter for the given key
     */
    public @Nullable String getString(String key) {
        final Object obj = this.parameters.get(key);
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    /**
     * This function returns an {@link Integer} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as an {@link Integer} or null if there is no value on the specified key.
     * @see NumberConversions#toInt(Object)
     */
    public @Nullable Integer getInteger(String key) {
        return NumberConversions.toInt(this.parameters.get(key));
    }

    /**
     * This function returns a {@link Float} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Float} or null if there is no value on the specified key.
     * @see NumberConversions#toFloat(Object)
     */
    public @Nullable Float getFloat(String key) {
        return NumberConversions.toFloat(this.parameters.get(key));
    }

    /**
     * This function returns a {@link Double} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Double} or null if there is no value on the specified key.
     * @see NumberConversions#toDouble(Object)
     */
    public @Nullable Double getDouble(String key) {
        return NumberConversions.toDouble(this.parameters.get(key));
    }

    /**
     * This function returns a {@link Long} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Long} or null if there is no value on the specified key.
     * @see NumberConversions#toLong(Object)
     */
    public @Nullable Long getLong(String key) {
        return NumberConversions.toLong(this.parameters.get(key));
    }

    /**
     * This function returns a {@link Short} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Short} or null if there is no value on the specified key.
     * @see NumberConversions#toShort(Object)
     */
    public @Nullable Short getShort(String key) {
        return NumberConversions.toShort(this.parameters.get(key));
    }

    /**
     * This function returns a {@link Byte} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Byte} or null if there is no value on the specified key.
     * @see NumberConversions#toByte(Object)
     */
    public @Nullable Byte getByte(String key) {
        return NumberConversions.toByte(this.parameters.get(key));
    }
}
