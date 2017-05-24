package com.rarnu.base.app

import android.content.res.Configuration
import com.rarnu.base.R
import com.rarnu.base.app.inner.InnerActivity

/**
 * Created by rarnu on 3/25/16.
 */
abstract class BasePopupActivity: InnerActivity() {

    companion object {
        private var screenState1 = -1
        private var screenState2 = -1
    }

    override fun getCloseCondition(): Boolean {
        var ret =  false
        if (screenState1 == -1 && screenState2 == -1) {
            screenState1 = resources.configuration.orientation
            screenState2 = resources.configuration.orientation
        } else {
            screenState1 = screenState2
            screenState2 = resources.configuration.orientation
        }
        if (screenState1 == Configuration.ORIENTATION_LANDSCAPE && screenState2 == Configuration.ORIENTATION_PORTRAIT) {
            screenState1 = -1
            screenState2 = -1
            ret = true
        }
        return ret
    }

    override fun getBaseLayout(): Int = R.layout.layout_popup_replacement

    override fun getReplaceId(): Int = R.id.fPopupReplacement
}