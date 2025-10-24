
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
        return Order.builder()
                    .id(id)
                    .petId(Utils.getRandomInt())
                    .quantity(Utils.getRandomInt())
                    .shipDate(LocalDateTime.now().toString())
                    .status(StoreUtils.getRandomStatus())
                    .complete(new Random().nextBoolean())
                    .build();
    }

}
