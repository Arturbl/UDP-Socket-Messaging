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
    public static String getPinByName(String nickname) {
        // if user is registered, then return pin
        if ( !UserDataHandler.isValidNickname(nickname) ) {
            return UserDataHandler.getUserByNickname(nickname)[1]; // [0] -> nickname | [1] -> pin
        }
        return null;
    }

    public static String getNameByPin(String pin) {
        // if pin is registered, then return name
        if ( !UserDataHandler.isValidPin(pin) ) {
            try {
                return UserDataHandler.getUserByPin(pin)[0]; // if the user inserts a pin outside the range, the app will crash without try/catch
            } catch (Exception ignored) {}
        }
        return null;
    }

}
