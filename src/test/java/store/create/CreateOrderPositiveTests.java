package store.create;

import api.store.models.Order;
import helper.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import steps.store.StoreAsserts;
import steps.store.StoreSteps;
import steps.store.StoreWaiters;
import store.BaseStoreTest;

@Epic("Petstore")
@Feature("Store")
public class CreateOrderPositiveTests extends BaseStoreTest {

    @Test(description = "Successfully create order")
    public void successfullyCreateOrderTest() {
        var orderId = generateRandomOrderId();
        var randomOrder = OrderCreationalHelpers.createRandomOrder(orderId);
        var createdOrder = StoreSteps.placeOrderSuccessfully(randomOrder);
        toDeleteOrderIds.add(createdOrder.getId());
        StoreAsserts.assertOrdersMatch(createdOrder, randomOrder);
        //TODO add waiter with check db that order is created instead of polling get order by id
        StoreWaiters.waitForOrderBeCreated(orderId);
    }

    @Test(description = "Place order without id successfully")
    public void placeOrderWithEmptyBodySuccessTest() {
        var randomOrder = Order.builder().build();
        StoreSteps.placeOrder(randomOrder);
    }

    @Test(description = "Create order schema validation test")
    public void createOrderSchemaValidationTest() {
        StoreSteps.placeOrder(OrderCreationalHelpers.createRandomOrder(generateRandomOrderId()))
                  .assertThat()
                  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/store/create-order.json"));
    }

}
