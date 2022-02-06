package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;

public class OrderListStellarBurgersTest {
    private UserClientStellarBurgers userClient;
    private UserStellarBurgers user;
    List<String> ingredients = new ArrayList<>();
    private OrderClientStellarBurgers orderClient;
    public String orderIngredients;

    @Before
    public void setUp() {
        userClient = new UserClientStellarBurgers();
        user = UserStellarBurgers.getRandomUser();
        orderClient = new OrderClientStellarBurgers();
        ingredients = new IngredientsClientStellarBurgers().getIngredients().extract().path("data._id");
        orderIngredients = ingredients.get(0);
    }

    @Test
    @DisplayName("Checking user can get order list with after authorization")
    public void checkingThatTheUserCanGetListOfOrderAfterAuthorizationTest() {
        String token = userClient.createUser(user).extract().path("accessToken");
        IngredientsHashesStellarBurgers createOrder = new IngredientsHashesStellarBurgers(orderIngredients);
        ValidatableResponse responseOrderCreation = new OrderClientStellarBurgers().createOrder(createOrder, token);

        ValidatableResponse responseList = orderClient.getOrderList(token);
        List<Map<String, String >> orderList = responseList.extract().path("orders");
        int statusCode = responseList.extract().statusCode();
        boolean isOrderCreationSuccess = responseList.extract().path("success");

        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreationSuccess);
        assertThat("Список заказов пуст", orderList, hasSize(1));
    }

    @Test
    @DisplayName("Checking user can't get order list without without authorization")
    public void checkngThatUserCannotGetListOfOrdersWithoutAuthorizationTest() {
        ValidatableResponse responseList = orderClient.getOrderList("");
        int statusCode = responseList.extract().statusCode();
        boolean isNotCanGetOrderList = responseList.extract().path("message").equals("You should be authorised");

        assertThat("Некорректный код статуса", statusCode, equalTo(401));
        assertTrue("Заказ не создан", isNotCanGetOrderList);
    }

}
