package store.order;

import api.store.StoreApi;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import utils.Utils;

import java.util.ArrayList;

@Epic("Pet store")
@Feature("Store")
public abstract class BaseStoreOrderTest {

    protected ArrayList<Integer> toDeleteOrderIds = new ArrayList<>();

    @Step("Generate random order id")
    protected static int generateRandomOrderId() {
        var nextId = Utils.getRandomInt();
        while (StoreApi.getOrderById(nextId).extract().statusCode() == 200) {
            nextId = nextId + Utils.getRandomInt() + 2000;
        }
        return nextId;
    }

}
