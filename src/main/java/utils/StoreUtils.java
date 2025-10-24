package utils;

import enums.Status;

import java.util.Random;

public final class StoreUtils {

    private static final Random RANDOM = new Random();

    private StoreUtils() {
        BaseUtility.getException();
    }

    public static Status getRandomStatus() {
        return Status.values()[RANDOM.nextInt(Status.values().length)];
    }

}
