package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationStellarBurgersTest {
    List<String> ingredients = new ArrayList<>();
    private UserClientStellarBurgers userClient;
    private UserStellarBurgers user;
    private int orderNumber;

    @Before
    public void setUp() {
        user = UserStellarBurgers.getRandomUser();
        userClient = new UserClientStellarBurgers();
    }

    @Test
    @DisplayName("Checking user can create order with ingredients after authorization")
    public void checkingPossibilityToPlaceAnOrderAfterAuthorizationTest() {
        String token = userClient.createUser(user).extract().path("accessToken");
        ingredients = new IngredientsClientStellarBurgers().getIngredients().extract().path("data._id");
        IngredientsHashesStellarBurgers orderIngredients = new IngredientsHashesStellarBurgers(ingredients.get(0));
        ValidatableResponse response = new OrderClientStellarBurgers().createOrder(orderIngredients, token);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreationSuccess = response.extract().path("success");
        orderNumber = response.extract().path("order.number");

        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreationSuccess);
        assertThat("Отсутствует номер заказа", orderNumber, notNullValue());
    }

    @Test
    @DisplayName("Checking user can create order with ingredients without authorization")
    public void checkingThePossibilityToPlaceAnOrderWithoutAuthorizationTest() {
        ingredients = new IngredientsClientStellarBurgers().getIngredients().extract().path("data._id");
        IngredientsHashesStellarBurgers orderIngredients = new IngredientsHashesStellarBurgers(ingredients.get(0));
        ValidatableResponse response = new OrderClientStellarBurgers().createOrder(orderIngredients, "");
        int statusCode = response.extract().statusCode();
        boolean isOrderCreationSuccess = response.extract().path("success");
        int orderNumber = response.extract().path("order.number");

        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreationSuccess);
        assertThat("Отсутствует номер заказа", orderNumber, notNullValue());
    }

    @Test
    @DisplayName("Checking user can't create order without ingredients")
    public void checkingThatTheUserCannotPlaceAnOrderWithoutIngredientsTest() {
        String token = userClient.createUser(user).extract().path("accessToken");
        IngredientsHashesStellarBurgers orderIngredients = new IngredientsHashesStellarBurgers("");
        ValidatableResponse response = new OrderClientStellarBurgers().createOrder(orderIngredients, token);
        int statusCode = response.extract().statusCode();
        boolean isOrderNotCreated = response.extract().path("message").equals("Ingredient ids must be provided");

        assertThat("Некорректный код статуса", statusCode, equalTo(400));
        assertTrue("Создан заказ с пустым списком ингредиентов", isOrderNotCreated);
    }

    @Test
    @DisplayName("Checking user can't create order with uncorrected ids of ingredients")
    public void checkngThatTheUserCannotOrderWithIncorrectIngredientIDsTest() {
        String token = userClient.createUser(user).extract().path("accessToken");
        IngredientsHashesStellarBurgers orderIngredients = new IngredientsHashesStellarBurgers("qwerty");
        ValidatableResponse response = new OrderClientStellarBurgers().createOrder(orderIngredients, token);
        int statusCode = response.extract().statusCode();

        assertThat("Некорректный код статуса", statusCode, equalTo(500));
    }

}
