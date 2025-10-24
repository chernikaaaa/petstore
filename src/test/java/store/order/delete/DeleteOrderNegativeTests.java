package store.order.delete;

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
import steps.store.StoreWaiters;
import store.order.BaseStoreOrderTest;
import utils.Utils;

import java.util.Random;

import static io.restassured.RestAssured.given;

@Epic("Pet store")
@Feature("Store")
public class DeleteOrderNegativeTests extends BaseStoreOrderTest {

    private Order orderToDelete2;

    @BeforeClass(alwaysRun = true)
    public void setupPreconditions() {
        var orderId = generateRandomOrderId();
        orderToDelete2 = helpers.OrderCreationalHelpers.createRandomOrder(orderId);
        StoreSteps.placeOrder(orderToDelete2);
        //TODO add waiter with check db that order is created instead of polling get order by id
        StoreWaiters.waitForOrderBePlaced(orderId);
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

    @Test(description = "Delete order without token test")
    public void deleteOrderWithoutTokenTest() {
        //401 error handling
        //if this were a real project with a secured API and defined user roles
    }

    @Test(description = "Delete order with user without accesses test")
    public void deleteOrderWithUserWithoutAccessesTest() {
        //403 error handling
        //if this were a real project with a secured API and defined user roles
    }

}
