package com.dididi.pocket.ec.sign;

import com.dididi.pocket.core.util.PocketPreferences;
import com.dididi.pocket.core.app.AccountManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by dididi
 * on 30/08/2018 .
 */

public class SignHandler {
    public static void onSignIn(String response, ISignListener listener) {
        JsonObject profile = new JsonParser().parse(response).getAsJsonObject();
        String token = profile.get("token").toString();
        String userName = profile.getAsJsonObject("user").get("name").toString();
        String userAvatar = profile.getAsJsonObject("user").get("avatar").toString();
        String userEmail = profile.getAsJsonObject("user").get("email").toString();

        PocketPreferences.addCustomPocketProfile("token", token);
        PocketPreferences.addCustomPocketProfile("userName", userName);
        PocketPreferences.addCustomPocketProfile("userAvatar", userAvatar);
        PocketPreferences.addCustomPocketProfile("userEmail", userEmail);

        AccountManager.setSignState(true);
        listener.onSignInSuccess();
    }

    public static void onSignUp(String response, ISignListener listener) {
        AccountManager.setSignState(true);
        listener.onSignUpSuccess();
    }
}
