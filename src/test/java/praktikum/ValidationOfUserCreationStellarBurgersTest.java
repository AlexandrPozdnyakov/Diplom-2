package praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ValidationOfUserCreationStellarBurgersTest {

    private UserClientStellarBurgers userClient;
    private UserStellarBurgers user;
    private int expectedStatus;
    private String expectedErrorMessage;

    @Before
    public void setUp() {
        userClient = new UserClientStellarBurgers();
    }

    public ValidationOfUserCreationStellarBurgersTest(UserStellarBurgers user, int expectedStatus, String expectedErrorMessage) {
        this.user = user;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {UserStellarBurgers.getUserWithoutEmail(), 403, "Email, password and name are required fields"},
                {UserStellarBurgers.getUserWithoutName(), 403, "Email, password and name are required fields"},
                {UserStellarBurgers.getUserWithoutPassword(), 403, "Email, password and name are required fields"},
        };
    }

    @Test
    @DisplayName("Checking field validation for user creation")
    public void checkngFieldsValidationUserCreationValidationTest() {
        ValidatableResponse response = userClient.createUser(user);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertEquals("Некорректный код статуса", expectedStatus, statusCode);
        assertEquals("Некорректное сообщение об ошибке", expectedErrorMessage, errorMessage);
    }

}
