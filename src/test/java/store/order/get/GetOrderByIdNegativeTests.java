package store.order.get;

import api.BaseClient;
import api.store.StoreApi;
import endpoints.Endpoints;
import helpers.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.common.CommonAsserts;
import steps.store.StoreSteps;
import steps.store.StoreWaiters;
import store.order.BaseStoreOrderTest;
import utils.Utils;

import java.util.Random;

import static io.restassured.RestAssured.given;

@Epic("Pet store")
@Feature("Store")
public class GetOrderByIdNegativeTests extends BaseStoreOrderTest {

    @Test(description = "Get order not found")
    public void getOrderNotFoundTest() {
        var response = StoreApi.getOrderById(generateRandomOrderId()).statusCode(404);
        CommonAsserts.assertFailedResponseMessage(response, "Order not found");
    }

    @Test(description = "Get order failed 400 because of incorrect ints", dataProvider = "invalidOrderIds")
    public void getOrderWithIncorrectIntInIdTest(Integer invalidOrderId) {
        StoreApi.getOrderById(invalidOrderId).statusCode(400);
    }

    @DataProvider()
    public Object[][] invalidOrderIds() {
        return new Object[][]{
                {0},
                {-1},
        };
    }

    //in this order back should return 400 according to swagger doc
    @Test(description = "Get order failed 400 because of incorrect string format id")
    public void getOrderWithStringFormatIdTest() {
        given()
                .spec(BaseClient.spec)
                .pathParam("orderId", Utils.getRandomString(3))
                .when()
                .get(Endpoints.GET_ORDER_BY_ID)
                .then()
                .statusCode(400);
    }

    //in this order back should return 400 according to swagger doc
    @Test(description = "Get order failed 400 because of incorrect double format id")
    public void getOrderWithDoubleFormatIdTest() {
        given()
                .spec(BaseClient.spec)
                .pathParam("orderId", new Random().nextDouble())
                .when()
                .get(Endpoints.GET_ORDER_BY_ID)
                .then()
                .statusCode(400);
    }

    //in this order back should return 400 or 405 but there isn't info about this in swagger doc
    @Test(description = "Get order failed 400 because of empty id")
    public void getOrderWithEmptyIdTest() {
        given()
                .spec(BaseClient.spec)
                .pathParam("orderId", "")
                .when()
                .get(Endpoints.GET_ORDER_BY_ID)
                .then()
                .statusCode(400);
    }

    @Test(description = "Get deleted order not found")
    public void getDeletedOrderNotFoundTest() throws InterruptedException {
        var orderId = generateRandomOrderId();

        StoreSteps.placeOrderSuccessfully(OrderCreationalHelpers.createRandomOrder(orderId));

        //TODO add waiter with check db that order is created instead of polling get order by id
        StoreWaiters.waitForOrderBePlaced(orderId);

        StoreApi.deleteOrder(orderId).statusCode(200);
        //TODO add waiter with check that order is really deleted from db, instead of sleep as it is bad practice
        Thread.sleep(10000);

        var response = StoreApi.getOrderById(orderId).statusCode(404);
        CommonAsserts.assertFailedResponseMessage(response, "Order not found");
    }

}
