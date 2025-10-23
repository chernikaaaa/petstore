package endpoints;

import utils.BaseUtility;

public final class Endpoints {

    private Endpoints() {
        BaseUtility.getException();
    }

    public static final String GET_INVENTORY = "/store/inventory";
    public static final String PLACE_ORDER = "/store/order";
    public static final String GET_ORDER_BY_ID = "/store/order/{orderId}";
    public static final String DELETE_ORDER = GET_ORDER_BY_ID;

}
