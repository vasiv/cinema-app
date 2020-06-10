package pl.kielce.tu.session;

import pl.kielce.tu.model.User;

/**
 * @author ciepluchs
 */
public class Session {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user1) {
        user = user1;
    }
}
