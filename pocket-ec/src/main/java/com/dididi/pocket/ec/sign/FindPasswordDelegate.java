package com.dididi.pocket.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dididi.pocket.ec.R;
import com.dididi.pocket.ec.R2;
import com.dididi.pocket_core.delegates.PocketDelegate;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by dididi
 * on 24/07/2018 .
 */

public class FindPasswordDelegate extends PocketDelegate {

    @BindView(R2.id.find_password_email_edit)
    MaterialEditText mEmail = null;
    @BindView(R2.id.find_password_verify)
    MaterialEditText mVerify = null;

    @OnClick(R2.id.find_password_send_verify)
    void onClickSendVerify(){

    }

    @OnClick(R2.id.find_password_back_btn)
    void onClickBack(){

    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        super.setSwipeBackEnable(true);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_find_password;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
