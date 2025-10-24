package store.create;

import api.store.models.Order;
import helper.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import steps.store.StoreAsserts;
import steps.store.StoreSteps;
import store.BaseStoreTest;

@Epic("Petstore")
@Feature("Store")
public class CreateOrderPositiveTests extends BaseStoreTest {

    @Test(description = "Successfully create order")
    public void successfullyCreateOrderTest() {
        var randomOrder = OrderCreationalHelpers.createRandomOrder(generateRandomOrderId());
        var createdOrder = StoreSteps.placeOrder(randomOrder)
                                     .extract()
                                     .response()
                                     .as(Order.class);
        toDeleteOrderIds.add(createdOrder.getId());
        StoreAsserts.assertOrdersMatch(createdOrder, randomOrder);
    }

    @Test(description = "Place order without id successfully")
    public void placeOrderWithEmptyBodySuccessTest() {
        var randomOrder = Order.builder().build();
        StoreSteps.placeOrder(randomOrder);
    }

}
