package steps.store;

import api.store.models.Order;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import utils.BaseUtility;

import java.util.Map;

public class StoreAsserts {

    private StoreAsserts() {
        BaseUtility.getException();
    }

    public static void assertGetInventorySuccessResponse(Map<Object, Object> response) {
        Assertions.assertThat(response).as("Inventory response should not be null").isNotNull();
        Assertions.assertThat(response)
                  .isInstanceOf(Map.class)
                  .as("Inventory response should not be empty")
                  .isNotEmpty();
        Assertions.assertThat(response).allSatisfy((key, value) -> SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(key).as("Inventory map key should be a String").isInstanceOf(String.class);
            softly.assertThat(value).as("Inventory map value should be a Double").isInstanceOf(Double.class);
        }));
    }

    public static void assertOrdersMatch(Order createdOrder, Order randomOrder) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(createdOrder)
                  .usingRecursiveComparison()
                  .ignoringFields("shipDate")
                  .as("Created order should match the sent order")
                  .isEqualTo(randomOrder);

            //cut to show only date and time without milliseconds and timezone for comparison
            softly.assertThat(createdOrder.getShipDate().substring(0, 19))
                  .as("Ship date should match")
                  .isEqualTo(randomOrder.getShipDate().substring(0, 19));
        });
    }

}
