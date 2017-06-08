package com.rarnu.base.sample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.rarnu.base.app.BaseFragment
import com.rarnu.base.utils.HttpMethod
import com.rarnu.base.utils.downloadAsync
import com.rarnu.base.utils.httpAsync
import kotlinx.android.synthetic.main.activity_main.view.*

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
        httpAsync {
            url = "http://rarnu.xyz/yugioh/update.php"
            method = HttpMethod.GET
            getParam = "ver=100"
            onSuccess { code, content, cookie ->
                activity.runOnUiThread {
                    Log.e("MainFragment", "code:$code, content:$content")
                }
            }
            onFail {
                activity.runOnUiThread {
                    Log.e("MainFragment", "${it?.message}")
                }
            }
        }

        downloadAsync(activity) {
            url = "http://rarnu.xyz/yugioh/image/p1.png"
            localDir = "/sdcard/"
            localFile = "p1.png"
            imageView = innerView.imgPic
        }
    }

    override fun getFragmentLayoutResId(): Int = R.layout.activity_main

    override fun getMainActivityName(): String? = null

    override fun initMenu(menu: Menu) {

    }

    override fun onGetNewArguments(bn: Bundle?) {

    }

    override fun getFragmentState(): Bundle? =  null
}