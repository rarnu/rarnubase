package com.rarnu.base.sample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.rarnu.base.app.BaseFragment
import com.rarnu.base.utils.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.File
import kotlin.concurrent.thread

/**
 * Created by rarnu on 6/2/17.
 */
class MainFragment : BaseFragment() {
    override fun getBarTitle(): Int = R.string.app_name

    override fun getBarTitleWithPath(): Int = 0

    override fun getCustomTitle(): String? = null

    override fun initComponents() {
    }

    override fun initEvents() {

    }

    override fun initLogic() {
        /*
        thread {
            val parser = PackageParserP.newPackageParser()
            val p = parser?.parsePackage(File(activity.filesDir, "a.apk"), 0)
            if (p != null) {
                Log.e(tag, "p.packageName => ${p.packageName}")
                Log.e(tag, "p.splitNames => ${p.splitNames}")
                Log.e(tag, "p.volumeUuid => ${p.volumeUuid}")
                Log.e(tag, "p.codePath => ${p.codePath}")
                Log.e(tag, "p.baseCodePath => ${p.baseCodePath}")
                Log.e(tag, "p.splitCodePaths => ${p.splitCodePaths}")
                Log.e(tag, "p.baseRevisionCode => ${p.baseRevisionCode}")
                Log.e(tag, "p.splitRevisionCodes => ${p.splitRevisionCodes}")
                Log.e(tag, "p.splitFlags => ${p.splitFlags}")
                Log.e(tag, "p.splitPrivateFlags => ${p.splitPrivateFlags}")
                Log.e(tag, "p.baseHardwareAccelerated => ${p.baseHardwareAccelerated}")
                p.activities?.forEach { Log.e(tag, "p.activity => ${it.className}") }
                p.services?.forEach { Log.e(tag, "p.service => ${it.className}") }
                p.receivers?.forEach { Log.e(tag, "p.receiver => ${it.className}") }
                p.providers?.forEach { Log.e(tag, "p.provider => ${it.className}") }
                p.permissions?.forEach { Log.e(tag, "p.permission => ${it.permissionInfo}") }
            }
        }
        */


        /*
        zip {
            zipPath = "/sdcard/a.zip"
            srcPath = "/sdcard/zipsample"
            success {
                Log.e("ZIP", "SUCC")
            }
            error {
                Log.e("ZIP", "FAIL => $it")
            }
        }
        */

        /*
        unzip {
            zipPath = "/sdcard/a.zip"
            destPath = "/sdcard/unzipsample"
            success {
                Log.e("UNZIP", "SUCC")
            }
            error {
                Log.e("UNZIP", "FAIL => $it")
            }
        }
        */


        /*
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

        */

        downloadAsync(activity) {
            url = "https://res.hjfile.cn/pt/hj/images/logo.png"
            localDir = "/sdcard/"
            localFile = "p1.png"
            imageView = innerView.imgPic
            progress { status, position, fileSize, errMsg ->
                Log.e("DOWNLOAD", "status:$status, position:$position, fileSize:$fileSize, errMsg:$errMsg")
            }
        }
    }

    override fun getFragmentLayoutResId(): Int = R.layout.activity_main

    override fun getMainActivityName(): String? = null

    override fun initMenu(menu: Menu) { }

    override fun onGetNewArguments(bn: Bundle?) {

    }

    override fun getFragmentState(): Bundle? = null
}