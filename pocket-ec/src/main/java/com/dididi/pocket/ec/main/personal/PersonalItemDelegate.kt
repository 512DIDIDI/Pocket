package com.dididi.pocket.ec.main.personal

import android.os.Bundle
import android.view.View

import com.dididi.pocket.core.delegates.bottom.BottomItemDelegate
import com.dididi.pocket.ec.R


/**
 * Created by dididi
 * on 24/07/2018 .
 */

class PersonalItemDelegate : BottomItemDelegate() {
    override fun setLayout(): Any {
        return R.layout.delegate_personal_personal
    }

    override fun onBindView(savedInstanceState: Bundle?, rootView: View) {

    }

    override fun onScrollToTop() {

    }

    override fun onRefresh() {

    }

    override fun isTop(): Boolean {
        return false
    }
}
