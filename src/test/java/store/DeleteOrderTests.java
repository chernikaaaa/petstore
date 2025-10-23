package store;

import api.store.StoreApi;
import api.store.models.Order;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.store.StoreSteps;

@Epic("Petstore")
@Feature("Store")
public class DeleteOrderTests extends BaseStoreTest {

    private Order orderToDelete;
    private Order orderToDelete2;

    @BeforeClass(alwaysRun = true)
    public void setupPreconditions() {
        orderToDelete = helper.OrderCreationalHelpers.createRandomOrder(generateRandomOrderId());
        orderToDelete2 = helper.OrderCreationalHelpers.createRandomOrder(generateRandomOrderId());
        StoreSteps.placeOrder(orderToDelete);
        StoreSteps.placeOrder(orderToDelete2);
        //TODO add waiter with check db that order is created (instead I use sleep but it is a bad practice)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //there isn't such status code in doc
    @Test(description = "Delete order successfully")
    public void deleteOrderSuccessfully() {
        StoreApi.deleteOrder(orderToDelete.id()).statusCode(200);
        //TODO add waiter with check that order is really deleted from db
    }

    @Test(description = "Delete order not found")
    public void deleteOrderNotFound() {
        StoreApi.deleteOrder(generateRandomOrderId()).statusCode(404);
    }

    @Test(description = "Delete same order two times")
    public void deleteSameOrderTwoTimes() {
        var id = orderToDelete2.id();
        StoreApi.deleteOrder(id).statusCode(200);
        //TODO add waiter with check that order is really deleted from db
        StoreApi.deleteOrder(id).statusCode(404);
    }

    @Test(description = "Delete order failed 400", dataProvider = "invalidOrderIds")
    public void deleteOrderFailed400(Integer invalidOrderId) {
        StoreApi.deleteOrder(invalidOrderId).statusCode(400);
    }

    @DataProvider()
    public Object[][] invalidOrderIds() {
        return new Object[][]{
                {0},
                {-1},
        };
    }

}
