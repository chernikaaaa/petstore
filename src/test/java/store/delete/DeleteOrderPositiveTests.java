package store.delete;

import api.store.StoreApi;
import api.store.models.Order;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import steps.store.StoreSteps;
import steps.store.StoreWaiters;
import store.BaseStoreTest;

@Epic("Petstore")
@Feature("Store")
public class DeleteOrderPositiveTests extends BaseStoreTest {

    private Order orderToDelete;

    @BeforeClass(alwaysRun = true)
    public void setupPreconditions() {
        var orderId = generateRandomOrderId();
        orderToDelete = helpers.OrderCreationalHelpers.createRandomOrder(orderId);
        StoreSteps.placeOrder(orderToDelete);
        //TODO add waiter with check db that order is created instead of polling get order by id
        StoreWaiters.waitForOrderBeCreated(orderId);
    }

    //there isn't such status code in doc
    @Test(description = "Delete order successfully")
    public void deleteOrderSuccessfully() {
        StoreApi.deleteOrder(orderToDelete.getId()).statusCode(200);
        //TODO add waiter with check that order is really deleted from db
    }

}
