package com.rarnu.base.sample

import android.app.Fragment
import com.rarnu.base.app.BaseActivity

class MainActivity : BaseActivity() {
    override fun getIcon(): Int = R.drawable.ic_launcher

    override fun replaceFragment(): Fragment = MainFragment()

    override fun customTheme(): Int = 0

    override fun getActionBarCanBack(): Boolean = false

}
