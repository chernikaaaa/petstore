package steps.store;

import api.store.StoreApi;
import api.store.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import utils.BaseUtility;

public class StoreSteps {

    private StoreSteps() {
        BaseUtility.getException();
    }

    @Step("Place order: {order}")
    public static ValidatableResponse placeOrder(Order order) {
        return StoreApi.placeOrder(order)
                       .statusCode(200);
    }

    @Step("Place order: {order} successfully")
    public static Order placeOrderSuccessfully(Order order) {
        return placeOrder(order)
                .extract()
                .response()
                .as(Order.class);
    }

}
