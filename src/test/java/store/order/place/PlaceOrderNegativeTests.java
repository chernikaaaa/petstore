package store.order.place;

import api.BaseClient;
import helpers.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.common.CommonAsserts;
import store.order.BaseStoreOrderTest;
import utils.Utils;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@Epic("Petstore")
@Feature("Store")
public class PlaceOrderNegativeTests extends BaseStoreOrderTest {

    @Test(description = "Place order with empty body failed test")
    public void placeOrderWithEmptyBodyFailedTest() {
        var response = given()
                .spec(BaseClient.spec)
                .when()
                .post("/store/order")
                .then()
                .statusCode(400);
        CommonAsserts.assertFailedResponseMessage(response, "No data");
    }

    @DataProvider(name = "invalidOrders")
    public Object[][] invalidOrders() {
        var randomOrder = OrderCreationalHelpers.createRandomOrder(generateRandomOrderId());
        var orderMap = Utils.objectToMap(randomOrder);

        var orderWithInvalidId = new HashMap<>(orderMap);
        orderWithInvalidId.put("id", "some string");
        var orderWithInvalidIdJson = Utils.toJson(orderWithInvalidId);

        var orderWithNegativeId = new HashMap<>(orderMap);
        orderWithNegativeId.put("id", -1);
        var orderWithNegativeIdJson = Utils.toJson(orderWithNegativeId);

        var orderWithInvalidPetId = new HashMap<>(orderMap);
        orderWithInvalidPetId.put("petId", "some string");
        var orderWithInvalidPetIdJson = Utils.toJson(orderWithInvalidPetId);

        var orderWithNegativePetId = new HashMap<>(orderMap);
        orderWithNegativePetId.put("petId", -1);
        var orderWithNegativePetIdJson = Utils.toJson(orderWithNegativePetId);

        var orderWithInvalidStatus = new HashMap<>(orderMap);
        orderWithInvalidStatus.put("status", "some string not from enum");
        var orderWithInvalidStatusJson = Utils.toJson(orderWithInvalidStatus);

        var orderWithInvalidQuantity = new HashMap<>(orderMap);
        orderWithInvalidQuantity.put("quantity", "some string");
        var orderWithInvalidQuantityJson = Utils.toJson(orderWithInvalidQuantity);

        var orderWithNegativeQuantity = new HashMap<>(orderMap);
        orderWithNegativeQuantity.put("quantity", -1);
        var orderWithNegativeQuantityJson = Utils.toJson(orderWithNegativeQuantity);

        var orderWithInvalidShipDate = new HashMap<>(orderMap);
        orderWithInvalidShipDate.put("shipDate", "some string");
        var orderWithInvalidShipDateJson = Utils.toJson(orderWithInvalidShipDate);

        var orderWithInvalidComplete = new HashMap<>(orderMap);
        orderWithInvalidComplete.put("complete", "some string");
        var orderWithInvalidCompleteJson = Utils.toJson(orderWithInvalidComplete);

        return new Object[][]{
                {
                        orderWithInvalidIdJson,
                        "Invalid id"
                },
                {
                        orderWithNegativeIdJson,
                        "Negative id"
                },
                {
                        orderWithInvalidPetIdJson,
                        "Invalid petId"
                },
                {
                        orderWithNegativePetIdJson,
                        "Negative petId"
                },
                {
                        orderWithInvalidStatusJson,
                        "Invalid status"
                },
                {
                        orderWithInvalidQuantityJson,
                        "Invalid quantity"
                },
                {
                        orderWithNegativeQuantityJson,
                        "Negative quantity"
                },
                {
                        orderWithInvalidShipDateJson,
                        "Invalid shipDate"
                },
                {
                        orderWithInvalidCompleteJson,
                        "Invalid complete"
                },
        };
    }

    //validations on dev side need to be added
    @Test(dataProvider = "invalidOrders")
    public void placeOrderWithInvalidDataFailedTest(String body, String expectedMessage) {
        var response = given()
                .spec(BaseClient.spec)
                .body(body)
                .when()
                .post("/store/order")
                .then()
                .statusCode(400);
        CommonAsserts.assertFailedResponseMessage(response, expectedMessage);
    }

}
