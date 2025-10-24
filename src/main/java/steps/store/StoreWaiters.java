package steps.store;

import api.store.StoreApi;
import api.store.models.Order;
import io.qameta.allure.Step;
import org.awaitility.Awaitility;
import utils.BaseUtility;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class StoreWaiters {

    private StoreWaiters() {
        BaseUtility.getException();
    }

    @Step("Wait for order with id {orderId} to be created")
    public static Order waitForOrderBeCreated(int orderId) {
        var result = new AtomicReference<Order>();
        Awaitility.await("Wait until order with id %s is created".formatted(orderId))
                  .atMost(Duration.ofSeconds(10))
                  .pollInterval(Duration.ofMillis(250))
                  .untilAsserted(() -> {
                      var returnedOrder = StoreApi.getOrderById(orderId).statusCode(200).extract().as(Order.class);
                      result.set(returnedOrder);
                  });
        return result.get();
    }

}
