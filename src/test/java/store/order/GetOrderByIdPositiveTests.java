package store.order;

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
import store.BaseStoreTest;

@Epic("Petstore")
@Feature("Store")
public class GetOrderByIdPositiveTests extends BaseStoreTest {

    private Order returnedOrder;
    private Order createdOrder;
    private Integer orderId;

    @BeforeClass(alwaysRun = true)
    private void setup() {
        orderId = generateRandomOrderId();
        var randomOrder = OrderCreationalHelpers.createRandomOrder(orderId);
        createdOrder = StoreSteps.placeOrderSuccessfully(randomOrder);
        //TODO add waiter with check db that order is created instead of polling get order by id
        returnedOrder = StoreWaiters.waitForOrderBeCreated(orderId);
    }

    @Test(description = "Get order by id successfully")
    public void getOrderSuccessfullyTest() {
        StoreAsserts.assertOrdersMatch(returnedOrder, createdOrder);
    }

    @Test(description = "Get order by id schema validation test")
    public void getOrderByIdSchemaValidationTest() {
        StoreApi.getOrderById(orderId)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/store/get-order.json"));
    }

}
