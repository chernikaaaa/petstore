package store.create;

import api.BaseClient;
import api.store.models.Order;
import helper.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import steps.store.StoreAsserts;
import steps.store.StoreSteps;
import store.BaseStoreTest;

import static io.restassured.RestAssured.given;

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
        toDeleteOrderIds.add(createdOrder.id());
        StoreAsserts.assertOrdersMatch(createdOrder, randomOrder);
    }


    @DataProvider(name = "invalidOrders")
    public Object[][] invalidOrders() {
        return new Object[][]{
                {
                        "{}",
                        "Empty JSON"
                },
                {
                        "{\"petId\":10,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false}",
                        "Missing id"
                },
                {
                        "{\"id\":1,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false}",
                        "Missing petId"
                },
                {
                        "{\"id\":-1,\"petId\":10,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false}",
                        "Negative id"
                },
                {
                        "{\"id\":1,\"petId\":10,\"quantity\":\"ten\",\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false}",
                        "String quantity"
                },
                {
                        "{\"id\":1,\"petId\":10,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"unknown\",\"complete\":false}",
                        "Invalid status"
                },
                {
                        "{\"id\":1,\"petId\":10,\"quantity\":1,\"shipDate\":\"23-10-2025\",\"status\":\"placed\",\"complete\":false}",
                        "Bad date format"
                },
                {
                        "{\"id\":1,\"petId\":10,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false,\"extraField\":\"unexpected\"}",
                        "Extra field"
                },
                {
                        "{\"id\":1,\"petId\":10,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":\"false\"}",
                        "Invalid boolean"
                },
                {
                        "{\"id\":null,\"petId\":10,\"quantity\":1,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false}",
                        "Null value"
                },
                {
                        "{\"id\":1,\"petId\":10,\"quantity\":0,\"shipDate\":\"2025-10-23T18:50:00Z\",\"status\":\"placed\",\"complete\":false}",
                        "Boundary value"
                }
        };
    }

    @Test(dataProvider = "invalidOrders")
    public void shouldReturn400ForInvalidOrders(String body, String caseName) {
        var response = given()
                .spec(BaseClient.spec)
                .body(body)
                .when()
                .post("/store/order")
                .then()
                .extract();

        int statusCode = response.statusCode();

        Assertions.assertThat(statusCode)
                  .as("Case: " + caseName)
                  .isEqualTo(400);
    }

}
