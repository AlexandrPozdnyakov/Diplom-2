package praktikum;

import com.github.javafaker.Faker;

public class UserStellarBurgers {

    public String email;
    public String password;
    public String name;
    public static Faker faker = new Faker();

    public UserStellarBurgers() {
    }

    public UserStellarBurgers(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserStellarBurgers getRandomUser() {
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password();
        final String name = faker.name().name();
        return new UserStellarBurgers(email, password, name);
    }

    public UserStellarBurgers setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserStellarBurgers setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserStellarBurgers setName(String name) {
        this.name = name;
        return this;
    }

    public static UserStellarBurgers getUserWithoutPassword() {
        return new UserStellarBurgers().setEmail(faker.internet().emailAddress()).setName(faker.name().name());
    }

    public static UserStellarBurgers getUserWithoutEmail() {
        return new UserStellarBurgers().setPassword(faker.internet().password()).setName(faker.name().name());
    }

    public static UserStellarBurgers getUserWithoutName() {
        return new UserStellarBurgers().setName(faker.internet().emailAddress()).setPassword(faker.internet().password());
    }

}
