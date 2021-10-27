package org.hbrs.se1.ws21.uebung4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utils for casting number types to other number types
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberConversions {

    public static int floor(double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int ceil(final double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor + (int) (~Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int round(double num) {
        return floor(num + 0.5d);
    }

    public static double square(double num) {
        return num * num;
    }

    public static @Nullable Integer toInt(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable Float toFloat(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        try {
            return Float.parseFloat(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable Double toDouble(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable Long toLong(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable Short toShort(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        }
        try {
            return Short.parseShort(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable Byte toByte(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Number) {
            return ((Number) obj).byteValue();
        }
        try {
            return Byte.parseByte(obj.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static boolean isFinite(double num) {
        return Math.abs(num) <= Double.MAX_VALUE;
    }

    public static boolean isFinite(float num) {
        return Math.abs(num) <= Float.MAX_VALUE;
    }

    public static void checkFinite(double num, @NotNull String message) {
        if (!isFinite(num)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkFinite(float num, @NotNull String message) {
        if (!isFinite(num)) {
            throw new IllegalArgumentException(message);
        }
    }
}
