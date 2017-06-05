package com.rarnu.base.sample

import android.os.Bundle
import android.view.Menu
import com.rarnu.base.app.BaseFragment

/**
 * Created by rarnu on 6/2/17.
 */
class MainFragment: BaseFragment() {
    override fun getBarTitle(): Int = R.string.app_name

    override fun getBarTitleWithPath(): Int = 0

    override fun getCustomTitle(): String? = null

    override fun initComponents() {

    }

    override fun initEvents() {

    }

    override fun initLogic() {

    }

    override fun getFragmentLayoutResId(): Int = R.layout.activity_main

    override fun getMainActivityName(): String? = null

    override fun initMenu(menu: Menu) {

    }

    override fun onGetNewArguments(bn: Bundle?) {

    }

    override fun getFragmentState(): Bundle? =  null
}