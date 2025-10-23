package api.store;

import api.BaseClient;
import api.store.models.Order;
import endpoints.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class StoreApi {

    private static final Logger LOG = LoggerFactory.getLogger(StoreApi.class);

    @Step("Get inventory")
    public static ValidatableResponse getInventory() {
        LOG.info("Getting inventory");
        return given()
                .spec(BaseClient.spec)
                .when()
                .get(Endpoints.GET_INVENTORY)
                .then();
    }

    @Step("Place order")
    public static ValidatableResponse placeOrder(Order order) {
        LOG.info("Placing order: {}", order);
        return given()
                .spec(BaseClient.spec)
                .body(order)
                .when()
                .post(Endpoints.PLACE_ORDER)
                .then();
    }

    @Step("Get order by id={orderId}")
    public static ValidatableResponse getOrderById(Integer orderId) {
        LOG.info("Getting order by id={}", orderId);
        return given()
                .spec(BaseClient.spec)
                .pathParam("orderId", orderId)
                .when()
                .get(Endpoints.GET_ORDER_BY_ID)
                .then();
    }

    @Step("Delete order by id={orderId}")
    public static ValidatableResponse deleteOrder(Integer orderId) {
        LOG.info("Deleting order by id={}", orderId);
        return given()
                .spec(BaseClient.spec)
                .pathParam("orderId", orderId)
                .when()
                .delete(Endpoints.DELETE_ORDER)
                .then();
    }

}
