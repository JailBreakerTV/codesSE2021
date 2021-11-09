package org.hbrs.se1.ws21.uebung4.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for providing some useful {@link Collection} based extension functions
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {
    /**
     * This function will convert a {@link Set} of strings into a sorted {@link List}
     *
     * @param set which should be sorted and returned as a list
     * @return List containing all the elements of the given set in a sorted way
     */
    public static List<String> sorted(Set<String> set) {
        return set.stream().sorted().collect(Collectors.toList());
    }

    /**
     * This function is responsible for joining the elements of the given collection
     * into a single string where the elements are separated by a given delimiter
     *
     * @param collection which should be joined into a single line
     * @param delimiter  which represents the separator
     * @return String containing the collection in one line
     */
    public static String joinToString(Collection<String> collection, String delimiter) {
        return joinToString(collection, delimiter, "");
    }

    /**
     * This function is responsible for joining the elements of the given collection
     * into a single string where the elements are separated by a given delimiter
     *
     * @param collection which should be joined into a single line
     * @param delimiter  which represents the separator
     * @param def        which will be returned if the given collection is empty
     * @return String containing the collection in one line
     */
    public static String joinToString(Collection<String> collection, String delimiter, String def) {
        if (collection.isEmpty()) {
            return def;
        }
        return String.join(delimiter, collection);
    }
}
