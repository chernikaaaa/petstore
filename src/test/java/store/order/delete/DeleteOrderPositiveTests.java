package store.order.delete;

import api.store.StoreApi;
import api.store.models.Order;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.store.StoreSteps;
import steps.store.StoreWaiters;
import store.order.BaseStoreOrderTest;

@Epic("Petstore")
@Feature("Store")
public class DeleteOrderPositiveTests extends BaseStoreOrderTest {

    private Order orderToDelete;
    private Integer orderId;

    @BeforeClass(alwaysRun = true)
    public void setupPreconditions() {
        orderId = generateRandomOrderId();
        orderToDelete = helpers.OrderCreationalHelpers.createRandomOrder(orderId);
        StoreSteps.placeOrder(orderToDelete);
        //TODO add waiter with check db that order is created instead of polling get order by id
        StoreWaiters.waitForOrderBePlaced(orderId);
    }

    //there isn't such status code in doc
    @Test(description = "Delete order successfully")
    public void deleteOrderSuccessfully() {
        StoreApi.deleteOrder(orderId).statusCode(200);
        //TODO add waiter with check that order is really deleted from db
    }

}
