package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClientStellarBurgers extends RestAssuredClientStellarBurgers {
    private static final String INGREDIENTS_PATH = "api/ingredients";

    @Step("Get ingredients for order creation")
    public ValidatableResponse getIngredients() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then();
    }

}
