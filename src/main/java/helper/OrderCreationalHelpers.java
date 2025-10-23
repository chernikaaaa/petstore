
package helper;

import api.store.models.Order;
import io.qameta.allure.Step;
import utils.BaseUtility;
import utils.StoreUtils;
import utils.Utils;

import java.time.LocalDateTime;
import java.util.Random;

public class OrderCreationalHelpers {

    private OrderCreationalHelpers() {
        BaseUtility.getException();
    }

    @Step("Create random order with id {id}")
    public static Order createRandomOrder(Integer id) {
        return new Order(id,
                         Utils.getRandomInt(),
                         Utils.getRandomInt(),
                         LocalDateTime.now().toString(),
                         StoreUtils.getRandomStatus(),
                         new Random().nextBoolean());
    }

}
