package controller;

import helper.UserHelper;

public class UserController {

    public static boolean registerNewUser(String nickname, String pin) {
        if (nickname.isEmpty() || pin.isEmpty()) {
            return false;
        }
        return UserHelper.registerNewUser(nickname, pin);
    }

    public static String getPinByName(String nickname) {
        if (nickname.isEmpty()) {
            return null;
        }
        return UserHelper.getPinByName(nickname);
    }

    public static String getNameByPin(String pin) {
        if (pin.isEmpty()) {
            return null;
        }
        return UserHelper.getNameByPin(pin);
    }

}
