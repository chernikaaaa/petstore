package steps.common;

import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import utils.BaseUtility;

public class CommonAsserts {

    private CommonAsserts() {
        BaseUtility.getException();
    }

    public static void assertFailedResponseMessage(ValidatableResponse validatableResponse, String expectedMessage) {
        var response = validatableResponse
                .extract()
                .response();
        var message = response.jsonPath().getString("message");
        Assertions.assertThat(message).as("Message from response should be equal to - ").isEqualTo(expectedMessage);
    }

}
