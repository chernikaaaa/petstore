package store.delete;

import api.BaseClient;
import api.store.StoreApi;
import api.store.models.Order;
import endpoints.Endpoints;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.store.StoreSteps;
import store.BaseStoreTest;
import utils.Utils;

import java.util.Random;

import static io.restassured.RestAssured.given;

@Epic("Petstore")
@Feature("Store")
public class DeleteOrderNegativeTests extends BaseStoreTest {

    private Order orderToDelete2;

    @BeforeClass(alwaysRun = true)
    public void setupPreconditions() {
        orderToDelete2 = helper.OrderCreationalHelpers.createRandomOrder(generateRandomOrderId());
        StoreSteps.placeOrder(orderToDelete2);
        //TODO add waiter with check db that order is created (instead I use sleep but it is a bad practice)
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Delete order not found")
    public void deleteOrderNotFound() {
        StoreApi.deleteOrder(generateRandomOrderId()).statusCode(404);
    }

    @Test(description = "Delete same order two times")
    public void deleteSameOrderTwoTimesTest() {
        var id = orderToDelete2.getId();
        StoreApi.deleteOrder(id).statusCode(200);
        //TODO add waiter with check that order is really deleted from db
        StoreApi.deleteOrder(id).statusCode(404);
    }

    @Test(description = "Delete order failed 400 because of incorrect ints", dataProvider = "invalidOrderIds")
    public void deleteOrderWithIncorrectIntInIdTest(Integer invalidOrderId) {
        StoreApi.deleteOrder(invalidOrderId).statusCode(400);
    }

    @DataProvider()
    public Object[][] invalidOrderIds() {
        return new Object[][]{
                {0},
                {-1},
        };
    }

    //in this order back should return 400 according to swagger doc
    @Test(description = "Delete order failed 400 because of incorrect string format id")
    public void deleteOrderWithStringFormatIdTest() {
        given()
                .spec(BaseClient.spec)
                .pathParam("orderId", Utils.getRandomString(3))
                .when()
                .delete(Endpoints.DELETE_ORDER)
                .then()
                .statusCode(400);
    }

    //in this order back should return 400 according to swagger doc
    @Test(description = "Delete order failed 400 because of incorrect double format id")
    public void deleteOrderWithDoubleFormatIdTest() {
        given()
                .spec(BaseClient.spec)
                .pathParam("orderId", new Random().nextDouble())
                .when()
                .delete(Endpoints.DELETE_ORDER)
                .then()
                .statusCode(400);
    }

    //in this order back should return 400 or 405 but there isn't info about this in swagger doc
    @Test(description = "Delete order failed 400 because of empty id")
    public void deleteOrderWithEmptyIdTest() {
        given()
                .spec(BaseClient.spec)
                .pathParam("orderId", "")
                .when()
                .delete(Endpoints.DELETE_ORDER)
                .then()
                .statusCode(400);
    }

}
