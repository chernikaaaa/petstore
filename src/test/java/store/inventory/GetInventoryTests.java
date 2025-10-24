package store.inventory;

import api.store.StoreApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import steps.store.StoreAsserts;
import store.order.BaseStoreOrderTest;

import java.util.Map;

import static org.hamcrest.Matchers.isA;

@Epic("Pet store")
@Feature("Store")
public class GetInventoryTests extends BaseStoreOrderTest {

    @Test(description = "Get inventory returns map of status and quantity")
    public void inventoryReturnsMapTest() {
        var response = StoreApi.getInventory()
                               .statusCode(200)
                               .body("$", isA(Map.class))
                               .extract()
                               .as(new TypeRef<Map<Object, Object>>() {
                               });
        StoreAsserts.assertGetInventorySuccessResponse(response);
    }

    @Test(description = "Get inventory schema validation test")
    public void getInventorySchemaValidationTest() {
        StoreApi.getInventory()
                .statusCode(200)
                .body("$", isA(Map.class))
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/store/get-inventory.json"));
    }

    @Test(description = "Get inventory without token test")
    public void getInventoryWithoutTokenTest() {
        //401 error handling
        //if this were a real project with a secured API and defined user roles
    }

    @Test(description = "Get inventory with user without accesses test")
    public void getInventoryWithUserWithoutAccessesTest() {
        //403 error handling
        //if this were a real project with a secured API and defined user roles
    }

}
