package helper;

import data.UserDataHandler;

public class UserHelper {
    public static boolean registerNewUser(String nickname, String pin) {
        // check if user or pin are available
        if ( UserDataHandler.isValidNickname(nickname) && UserDataHandler.isValidPin(pin)) {
            return UserDataHandler.registerNewUser(nickname, pin);
        }
        return false;
    }
    public static String getPin(String nickname) {
        // if user is registered, then return pin
        if ( !UserDataHandler.isValidNickname(nickname) ) {
            return UserDataHandler.getUserByNickname(nickname)[1];
        }
        return null;
    }
}
