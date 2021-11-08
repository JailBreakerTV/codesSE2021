package org.hbrs.se1.ws21.uebung4.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {
    public static List<String> sorted(Set<String> set) {
        return set.stream().sorted().collect(Collectors.toList());
    }
}
