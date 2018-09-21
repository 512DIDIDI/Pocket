package com.dididi.pocket.core.delegates;

/**
 * Created by dididi
 * on 18/07/2018 .
 */

public abstract class PocketDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends PocketDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }

}
