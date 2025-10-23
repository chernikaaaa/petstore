package utils;

import api.store.models.Order;

import java.util.Random;

public final class StoreUtils {

    private static final Random RANDOM = new Random();

    private StoreUtils() {
        BaseUtility.getException();
    }

    public static Order.Status getRandomStatus() {
        return Order.Status.values()[RANDOM.nextInt(Order.Status.values().length)];
    }

}
