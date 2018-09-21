package com.dididi.pocket.core.app;

import com.dididi.pocket.core.Util.PocketPreferences;

/**
 * Created by dididi
 * on 02/09/2018 .
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    public static void setSignState(boolean state) {
        PocketPreferences.setPocketFlag(SignTag.SIGN_TAG.name(), state);
    }

    private static boolean isSignIn() {
        return PocketPreferences.getPocketFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker iUserChecker) {
        if (isSignIn()) {
            iUserChecker.onSignIn();
        } else {
            iUserChecker.onNotSignIn();
        }
    }
}
