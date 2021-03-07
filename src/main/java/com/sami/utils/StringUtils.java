package com.sami.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;


public class StringUtils {

    private static final int BASE_YEAR = 2000;

    public static final String FULL_TEST_SEARCH_META_CHARS = "[\\[\\]+*()<>@~\\-'\\\\]";

    private static final String[] BASE32_SYMBOLS = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H",
            "J", "K", "L", "M", "N", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z",
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    public static boolean isNotEmpty(String str) {
        return Objects.nonNull(str) && str.trim().length() > 0;
    }

    public static boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }

    public static boolean isEmptyArr(Set<?> strArr) {
        return strArr.size() == 0;
    }

    public static boolean isNumericString(String code) {
        return code.matches("[0-9]+");
    }

    public static boolean isAnyEmpty(String... strings) {
        return Arrays.stream(strings).anyMatch(StringUtils::isEmpty);
    }

    public static boolean isAllNotEmpty(String... strings) {
        return Arrays.stream(strings).noneMatch(StringUtils::isEmpty);
    }

    public static String joinWithDelimiter(String delimiter, String... values) {
        List<String> elements = Arrays.asList(values);

        StringBuilder sb = new StringBuilder("");
        elements.forEach(s -> {
            String str = StringUtils.isNotEmpty(s) ? s.trim() : "";

            if (sb.length() > 0) {
                sb.append(delimiter);
            }

            sb.append(str);
        });

        return sb.toString();
    }

    public static String appendWildCardInSearchValues(String searchVal) {
        return Arrays.stream(searchVal.split(" "))
                .map(StringUtils::escapeFullTextSearchMetaChar)
                .filter(StringUtils::isNotEmpty)
                .map(s -> "+" + s + "*")
                .collect(Collectors.joining(" "));
    }

    public static String escapeFullTextSearchMetaChar(String val) {
        return val.replaceAll(FULL_TEST_SEARCH_META_CHARS, "");
    }

    public static String[] splitString(String name) {
        return Arrays.stream(name.split(" "))
                .map(String::trim)
                .toArray(String[]::new);
    }

    public static String[] toArray(List<String> stringList) {
        String[] stringArray = new String[0];
        return CollectionUtils.isEmpty(stringList) ? stringArray : stringList.toArray(stringArray);
    }

    public static String generateUniqueId(String prefix) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear() - BASE_YEAR;
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int second = now.get(ChronoField.SECOND_OF_DAY);
        int millis = now.get(ChronoField.MILLI_OF_SECOND);

        return prefix + base32(year, 0) + base32(month, 0) + base32(day, 0) + base32(second, 4) + base32(millis, 2);
    }


    private static String base32(int decimal, int width) {
        StringBuilder result = new StringBuilder();
        int remainder;

        while (decimal > 0) {
            remainder = decimal % 32;
            decimal = decimal / 32;
            result.append(BASE32_SYMBOLS[remainder]);
        }

        return result.length() >= width
                ? result.reverse().toString()
                : String.format("%" + width + "s", result.reverse().toString()).replace(" ", BASE32_SYMBOLS[0]);
    }
    
    public static String trim(String str) {
		return str.trim();
	}
}
