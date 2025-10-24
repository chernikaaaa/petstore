package utils;

import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Utils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Random RANDOM = new Random();
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIALS = "!@#$%^&*()_+=-";
    public static final String ALPHA_PLUS_NUMS = ALPHA + DIGITS;

    private Utils() {
        BaseUtility.getException();
    }

    // Any characters (letters + digits + specials)
    public static String getRandomString(int length) {
        var allowedChars = ALPHA_PLUS_NUMS + SPECIALS;
        String result;

        do {
            result = IntStream.range(0, length)
                              .mapToObj(i -> String.valueOf(allowedChars.charAt(RANDOM.nextInt(allowedChars.length()))))
                              .collect(Collectors.joining());
        } while (!result.matches(".*[A-Za-z].*")   // at least one letter
                || !result.matches(".*\\d.*")      // at least one digit
                || !result.matches(".*[!@#$%^&*()_+=-].*")); // at least one special char

        return result;
    }

    // Only letters
    public static String getRandomStringOnlyWithLetters(int length) {
        return IntStream.range(0, length)
                        .mapToObj(i -> String.valueOf(ALPHA.charAt(RANDOM.nextInt(ALPHA.length()))))
                        .collect(Collectors.joining());
    }

    // Letters + numbers (must contain at least one of each)
    public static String getRandomStringWithLettersAndNumbers(int length) {
        String result;
        do {
            result = IntStream.range(0, length)
                              .mapToObj(i -> String.valueOf(ALPHA_PLUS_NUMS.charAt(RANDOM.nextInt(ALPHA_PLUS_NUMS.length()))))
                              .collect(Collectors.joining());
        } while (!result.matches(".*[A-Za-z].*") || !result.matches(".*\\d.*"));
        return result;
    }

    public static int getRandomInt() {
        var min = 10;
        var max = 1000;
        return getRandomInt(min, max);
    }

    public static int getRandomInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static String getRandomStringOnlyWithNumbers(int length) {
        return IntStream.range(0, length)
                        .mapToObj(i -> String.valueOf(DIGITS.charAt(RANDOM.nextInt(DIGITS.length()))))
                        .collect(Collectors.joining());
    }

    public static String toJson(Map<String, Object> order) {
        try {
            return OBJECT_MAPPER.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> objectToMap(Object obj) {
        return OBJECT_MAPPER.convertValue(obj, Map.class);
    }

}
