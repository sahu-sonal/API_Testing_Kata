package utils;

public class StringUtils {

    public static String normalizeString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}

