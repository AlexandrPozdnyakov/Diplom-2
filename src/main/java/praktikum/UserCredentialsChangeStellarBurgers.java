package praktikum;

import com.github.javafaker.Faker;

public class UserCredentialsChangeStellarBurgers {

    public String email;
    public String password;
    private String name;
    private static Faker faker = new Faker();

    public UserCredentialsChangeStellarBurgers() {
    }

    public UserCredentialsChangeStellarBurgers(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCredentialsChangeStellarBurgers from(UserStellarBurgers user) {
        return new UserCredentialsChangeStellarBurgers(user.email, user.password, user.name);
    }

    public UserCredentialsChangeStellarBurgers setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCredentialsChangeStellarBurgers setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserCredentialsChangeStellarBurgers setName(String name) {
        this.name = name;
        return this;
    }

    public static UserCredentialsChangeStellarBurgers changeUserCredentials() {
        return new UserCredentialsChangeStellarBurgers().setEmail(faker.internet().emailAddress())
                .setPassword(faker.internet().password())
                .setName(faker.name().name());
    }

}