package org.hbrs.se1.ws21.uebung4.util;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * This map implementation can automatically convert multiple values for a key into a list of values
 *
 * @param <K> which represents the key of this {@link HashMap} implementation
 */
public class MergingMap<K> extends HashMap<K, Object> {
    @Override
    public Object put(K key, Object value) {
        final Object obj = this.get(key);
        if (obj != null) {
            if (obj instanceof Collection) {
                final Collection<Object> collection = (Collection<Object>) obj;
                collection.add(value);
                return collection;
            }
            final Collection<Object> collection = new ArrayList<>();
            collection.add(obj);
            collection.add(value);
            return super.put(key, collection);
        }
        return super.put(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (key == null || value == null) {
            return false;
        }
        final Object obj = this.get(key);
        if (obj == null) {
            return false;
        }
        if (obj instanceof Collection) {
            final Collection<Object> collection = (Collection<Object>) obj;
            return collection.remove(value);
        }
        return super.remove(key, value);
    }

    /**
     * This function gets the current value for the given key and casts it to a {@link Collection}
     *
     * @param key of the parameter
     * @return Collection containing all parameters for the same key or
     * null if the value is not a subtype of {@link Collection}
     */
    public @Nullable Collection<Object> getCollection(K key) {
        final Object value = this.get(key);
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
    public @Nullable String getString(K key) {
        final Object obj = this.get(key);
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
    public @Nullable Integer getInteger(K key) {
        return NumberConversions.toInt(this.get(key));
    }

    /**
     * This function returns a {@link Float} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Float} or null if there is no value on the specified key.
     * @see NumberConversions#toFloat(Object)
     */
    public @Nullable Float getFloat(K key) {
        return NumberConversions.toFloat(this.get(key));
    }

    /**
     * This function returns a {@link Double} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Double} or null if there is no value on the specified key.
     * @see NumberConversions#toDouble(Object)
     */
    public @Nullable Double getDouble(K key) {
        return NumberConversions.toDouble(this.get(key));
    }

    /**
     * This function returns a {@link Long} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Long} or null if there is no value on the specified key.
     * @see NumberConversions#toLong(Object)
     */
    public @Nullable Long getLong(K key) {
        return NumberConversions.toLong(this.get(key));
    }

    /**
     * This function returns a {@link Short} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Short} or null if there is no value on the specified key.
     * @see NumberConversions#toShort(Object)
     */
    public @Nullable Short getShort(K key) {
        return NumberConversions.toShort(this.get(key));
    }

    /**
     * This function returns a {@link Byte} value for the specified key
     *
     * @param key of the parameter
     * @return Either the value as a {@link Byte} or null if there is no value on the specified key.
     * @see NumberConversions#toByte(Object)
     */
    public @Nullable Byte getByte(K key) {
        return NumberConversions.toByte(this.get(key));
    }
}
