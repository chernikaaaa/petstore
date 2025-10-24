package store.order.place;

import api.store.models.Order;
import helpers.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import steps.store.StoreAsserts;
import steps.store.StoreSteps;
import steps.store.StoreWaiters;
import store.order.BaseStoreOrderTest;

@Epic("Pet store")
@Feature("Store")
public class PlaceOrderPositiveTests extends BaseStoreOrderTest {

    @Test(description = "Successfully place order")
    public void successfullyPlaceOrderTest() {
        var orderId = generateRandomOrderId();
        var randomOrder = OrderCreationalHelpers.createRandomOrder(orderId);
        var placedOrder = StoreSteps.placeOrderSuccessfully(randomOrder);
        toDeleteOrderIds.add(placedOrder.getId());
        StoreAsserts.assertOrdersMatch(placedOrder, randomOrder);
        //TODO add waiter with check db that order is created instead of polling get order by id
        StoreWaiters.waitForOrderBePlaced(orderId);
    }

    @Test(description = "Place order without id successfully")
    public void placeOrderWithEmptyBodySuccessTest() {
        var randomOrder = Order.builder().build();
        var order = StoreSteps.placeOrderSuccessfully(randomOrder);
        createdOrderIds.add(order.getId());
    }

    @Test(description = "Create order schema validation test")
    public void placeOrderSchemaValidationTest() {
        StoreSteps.placeOrder(OrderCreationalHelpers.createRandomOrder(generateRandomOrderId()))
                  .assertThat()
                  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/store/place-order.json"));
    }

    @Test(description = "Place order by id without token test")
    public void placeOrderByIdWithoutTokenTest() {
        //401 error handling
        //if this were a real project with a secured API and defined user roles
    }

    @Test(description = "Place order by id with user without accesses test")
    public void placeOrderByIdWithUserWithoutAccessesTest() {
        //403 error handling
        //if this were a real project with a secured API and defined user roles
    }

}
