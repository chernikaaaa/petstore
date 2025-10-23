package store;

import api.store.StoreApi;
import helper.OrderCreationalHelpers;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Epic("Petstore")
@Feature("Store")
public class GetOrderByIdTests extends BaseStoreTest {

    @Test(description = "GET /store/order/{id} 400 for invalid id (outside 1..10)")
    public void getOrder400ForInvalidId() {
        StoreApi.getOrderById(0).statusCode(400);
        StoreApi.getOrderById(11).statusCode(400);
        StoreApi.getOrderById(-1).statusCode(400);
    }

    @Test(description = "GET /store/order/{id} 404 after order is deleted")
    public void getOrder404AfterDelete() {
        var order = OrderCreationalHelpers.createRandomOrder(generateRandomOrderId());

        int id = StoreApi.placeOrder(order).statusCode(anyOf(is(200), is(201))).extract().jsonPath().getInt("id");

        StoreApi.deleteOrder(id).statusCode(anyOf(is(200), is(204), is(404))); // petstore is flaky

        StoreApi.getOrderById(id).statusCode(404);
    }

}
