package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClientStellarBurgers extends RestAssuredClientStellarBurgers {
    private static final String USER_PATH = "api/auth";

    @Step("Create a user")
    public ValidatableResponse createUser(UserStellarBurgers user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then();
    }

    @Step ("Login of a user")
    public ValidatableResponse loginUser(UserCredentialsStellarBurgers credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "/login")
                .then();
    }

    @Step("Change user's credentials")
    public ValidatableResponse changeUser(UserCredentialsChangeStellarBurgers credentialsChange, String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .body(credentialsChange)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }

}
