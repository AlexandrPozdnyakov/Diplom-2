package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentialsStellarBurgers {

    public String email;
    public String password;

    public UserCredentialsStellarBurgers() {
    }

    public UserCredentialsStellarBurgers(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentialsStellarBurgers from(UserStellarBurgers user) {
        return new UserCredentialsStellarBurgers(user.email, user.password);
    }

    public UserCredentialsStellarBurgers setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCredentialsStellarBurgers setPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCredentialsStellarBurgers getUserPermissionWithNotValidCredentials() {
        return new UserCredentialsStellarBurgers().setEmail(RandomStringUtils.randomAlphabetic(15))
                .setPassword(RandomStringUtils.randomAlphabetic(10));
    }

}
