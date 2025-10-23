package utils;

public final class BaseUtility {

    private BaseUtility() {
        getException();
    }

    public static void getException() {
        throw new IllegalStateException("Utility class");
    }

}
