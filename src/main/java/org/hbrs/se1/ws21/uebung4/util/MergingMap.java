package org.hbrs.se1.ws21.uebung4.util;

import org.hbrs.se1.ws21.uebung4.util.NumberConversions;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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

    public @Nullable Collection<Object> getCollection(K key) {
        final Object value = this.get(key);
        if (value instanceof Collection) {
            return (Collection<Object>) value;
        }
        return null;
    }

    public @Nullable String getString(K key) {
        final Object obj = this.get(key);
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj.toString();
    }

    public @Nullable Integer getInteger(K key) {
        return NumberConversions.toInt(this.get(key));
    }

    public @Nullable Float getFloat(K key) {
        return NumberConversions.toFloat(this.get(key));
    }

    public @Nullable Double getDouble(K key) {
        return NumberConversions.toDouble(this.get(key));
    }

    public @Nullable Long getLong(K key) {
        return NumberConversions.toLong(this.get(key));
    }

    public @Nullable Short getShort(K key) {
        return NumberConversions.toShort(this.get(key));
    }

    public @Nullable Byte getByte(K key) {
        return NumberConversions.toByte(this.get(key));
    }
}
