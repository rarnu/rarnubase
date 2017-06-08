package com.rarnu.base.sample

import android.Manifest
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Bundle
import com.rarnu.base.app.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

    }

    override fun getIcon(): Int = R.drawable.ic_launcher

    override fun replaceFragment(): Fragment = MainFragment()

    override fun customTheme(): Int = 0

    override fun getActionBarCanBack(): Boolean = false

}
