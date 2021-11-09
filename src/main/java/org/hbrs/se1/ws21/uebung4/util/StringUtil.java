package org.hbrs.se1.ws21.uebung4.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtil {
    public static boolean isNumeric(@Nullable String input) {
        if (input == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(input);
            double d = Double.parseDouble(input);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
}
