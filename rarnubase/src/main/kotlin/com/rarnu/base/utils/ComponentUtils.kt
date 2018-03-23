package com.rarnu.base.utils

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.rarnu.base.command.Command

/**
 * Created by rarnu on 4/8/16.
 */
object ComponentUtils {

    fun enabledComponent(context: Context, receiverName: ComponentName?): Boolean {
        return try {
            Command.runCommand("pm enable '${receiverName?.packageName}/${receiverName?.className}'", true, null)
            context.packageManager.getComponentEnabledSetting(receiverName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        } catch (t: Throwable) {
            false
        }
    }

    fun disableComponent(context: Context, receiverName: ComponentName?): Boolean {
        return try {
            Command.runCommand("pm disable '${receiverName?.packageName}/${receiverName?.className}'", true, null)
            context.packageManager.getComponentEnabledSetting(receiverName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        } catch (t: Throwable) {
            false
        }
    }

    fun parsePackageInfo(info: PackageInfo?): Any? /* PackageParser.Package */ {
        val fileAbsPath = info?.applicationInfo?.publicSourceDir
        val parser = PackageParserP.newPackageParser()
        return parser?.parsePackage(fileAbsPath!!, PackageParserP.PARSE_IS_SYSTEM)
    }

    fun getPackageRSList(context: Context, /* PackageParser.Package */ pkg: Any?): MutableList<CompInfo?>? {
        val lstComponentInfo = arrayListOf<CompInfo?>()
        val pm = context.packageManager
        val lstReceiver = pkg?.receivers
        for (/* PackageParser.Activity */ a in lstReceiver!!) {
            val info = CompInfo()
            info.component = a
            info.fullPackageName = a.componentName?.className
            info.enabled = pm.getComponentEnabledSetting(a.componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            lstComponentInfo.add(info)
        }
        val lstService = pkg.services
        for (/* PackageParser.Service */ s in lstService!!) {
            val info = CompInfo()
            info.component = s
            info.fullPackageName = s.componentName?.className
            info.enabled = pm.getComponentEnabledSetting(s.componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            lstComponentInfo.add(info)
        }
        return lstComponentInfo
    }

    fun getActivityList(ctx: Context?, pkg: Any?): MutableList<CompInfo> {
        val lstComponentInfo = arrayListOf<CompInfo>()
        if (ctx != null) {
            val pm = ctx.packageManager
            val lst = pkg?.activities
            if (lst != null) {
                for (a in lst) {
                    val info = CompInfo()
                    info.component = a
                    info.fullPackageName = a.componentName?.className
                    info.enabled = pm.getComponentEnabledSetting(a.componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    lstComponentInfo.add(info)
                }
            }
        }
        lstComponentInfo.sortBy { it.compName }
        return lstComponentInfo
    }

    fun getServiceList(ctx: Context?, pkg: Any?): MutableList<CompInfo> {
        val lstComponentInfo = arrayListOf<CompInfo>()
        if (ctx != null) {
            val pm = ctx.packageManager
            val lst = pkg?.services
            if (lst != null) {
                for (a in lst) {
                    val info = CompInfo()
                    info.component = a
                    info.fullPackageName = a.componentName?.className
                    info.enabled = pm.getComponentEnabledSetting(a.componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    lstComponentInfo.add(info)
                }
            }
        }
        lstComponentInfo.sortBy { it.compName }
        return lstComponentInfo
    }

    fun getReceiverList(ctx: Context?, pkg: Any?): MutableList<CompInfo> {
        val lstComponentInfo = arrayListOf<CompInfo>()
        if (ctx != null) {
            val pm = ctx.packageManager
            val lst = pkg?.receivers
            if (lst != null) {
                for (a in lst) {
                    val info = CompInfo()
                    info.component = a
                    info.fullPackageName = a.componentName?.className
                    info.enabled = pm.getComponentEnabledSetting(a.componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    lstComponentInfo.add(info)
                }
            }
        }
        lstComponentInfo.sortBy { it.compName }
        return lstComponentInfo
    }

    fun getProviderList(ctx: Context?, pkg: Any?): MutableList<CompInfo> {
        val lstComponentInfo = arrayListOf<CompInfo>()
        if (ctx != null) {
            val pm = ctx.packageManager
            val lst = pkg?.providers
            if (lst != null) {
                for (a in lst) {
                    val info = CompInfo()
                    info.component = a
                    info.fullPackageName = a.componentName?.className
                    info.enabled = pm.getComponentEnabledSetting(a.componentName) != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    lstComponentInfo.add(info)
                }
            }
        }
        lstComponentInfo.sortBy { it.compName }
        return lstComponentInfo
    }

    fun getPackageComponentName(/* PackageParser.Component<?> */ comp: Any?) = comp?.componentName

    fun isServiceRunning(context: Context, className: String?): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val serviceList = activityManager.getRunningServices(30)
        if (serviceList.size <= 0) {
            return false
        }
        return serviceList.any { it.service.className == className }
    }


    class CompInfo {

        var component: Any? = null /* PackageParser.Component<?> */
        var enabled = false
        var position = 0
        var fullPackageName: String? = null

        val compName: String?
            get() = component?.className?.substringAfterLast(".")

        val intents: List<String>
            get() {
                val result = arrayListOf<String>()
                if (component != null && component?.intents != null) {
                    component!!.intents!!.filter { it.countActions() > 0 }.forEach { a -> (0 until a.countActions()).mapTo(result) { a.getAction(it) } }
                }
                return result
            }

        fun appendIntents(str: String?): String? {
            var nstr = str
            val pa = component!!
            if (pa.intents != null) {
                if (pa.intents!!.isNotEmpty()) {
                    for (aobj in pa.intents!!) {
                        if (aobj.countActions() > 0) {
                            for (i in 0 until aobj.countActions()) {
                                nstr += aobj.getAction(i).substringAfterLast(".").replace("_", "").toLowerCase() + "<br />"
                            }
                        }
                    }
                }
            }
            return nstr
        }

        fun isServiceRunning(context: Context): Boolean {
            var ret = false
            try {
                ret = isServiceRunning(context, component?.className)
            } catch (e: Throwable) {

            }
            return ret
        }
    }

    class EnableappInfo {
        var info: ApplicationInfo? = null
        var log: String? = null

        // 0: succ | 1: exists | 2: fail
        var logId = -1
        var enabled = false

        // 0: system | 1:data | 2: internal | 3: private
        var type = -1
        var filePath: String? = null
    }

}