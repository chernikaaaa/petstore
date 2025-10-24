package store.order.get;

import api.store.StoreApi;
import api.store.models.Order;
import helpers.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.store.StoreAsserts;
import steps.store.StoreSteps;
import steps.store.StoreWaiters;
import store.order.BaseStoreOrderTest;

@Epic("Petstore")
@Feature("Store")
public class GetOrderByIdPositiveTests extends BaseStoreOrderTest {

    private Order returnedOrder;
    private Order placedOrder;
    private Integer orderId;

    @BeforeClass(alwaysRun = true)
    private void setup() {
        orderId = generateRandomOrderId();
        var randomOrder = OrderCreationalHelpers.createRandomOrder(orderId);
        placedOrder = StoreSteps.placeOrderSuccessfully(randomOrder);
        //TODO add waiter with check db that order is created instead of polling get order by id
        returnedOrder = StoreWaiters.waitForOrderBePlaced(orderId);
    }

    @Test(description = "Get order by id successfully")
    public void getOrderSuccessfullyTest() {
        StoreAsserts.assertOrdersMatch(returnedOrder, placedOrder);
    }

    @Test(description = "Get order by id schema validation test")
    public void getOrderByIdSchemaValidationTest() {
        StoreApi.getOrderById(orderId)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/store/get-order.json"));
    }

}
