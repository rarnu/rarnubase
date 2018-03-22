package com.rarnu.base.utils

import android.content.ComponentName
import android.content.IntentFilter
import android.content.pm.*
import android.os.Bundle
import android.util.Log
import java.io.File

/**
 * ******************************************
 * ******************************************
 * INNER CLASS PACKAGEPARSER
 * ******************************************
 * ******************************************
 */

val Any.isPackageParser: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Exception) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isPackageParser" equals true.
// ==============================================================


fun Any.parsePackage(path: String, flag: Int) = parsePackage(File(path), flag)

fun Any.parsePackage(file: File, flag: Int): Any? {
    var ret: Any? = null
    try {
        val mParse = javaClass.getDeclaredMethod("parsePackage", File::class.java, Integer.TYPE)
        mParse?.isAccessible = true
        ret = mParse?.invoke(this, file, flag)
    } catch (e: Throwable) {
        Log.e("PARSE", "${e.message}")
    }
    return ret
}

fun Any.collectCertificates(pkg: Any, parseFlags: Int) {
    try {
        val cPackage = Class.forName("android.content.pm.PackageParser\$Package")
        val mCollectCertificates = javaClass.getDeclaredMethod("collectCertificates", cPackage, Int::class.java)
        mCollectCertificates.isAccessible = true
        mCollectCertificates.invoke(this, pkg, parseFlags)
    } catch (e: Throwable) {

    }
}

/**
 * ******************************************
 * ******************************************
 * INNER CLASS PACKAGE
 * ******************************************
 * ******************************************
 */

val Any.isPackage: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$Package")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Exception) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isPackage" equals true.
// ==============================================================

val Any.applicationInfo: ApplicationInfo?
    get() = PackageParserRef.getObjectValue(this, "applicationInfo") as ApplicationInfo?

val Any.packageName: String?
    get() = PackageParserRef.getStringValue(this, "packageName")

val Any.splitNames: Array<String>?
    get() = PackageParserRef.getStringArrayValue(this, "splitNames")

val Any.volumeUuid: String?
    get() = PackageParserRef.getStringValue(this, "volumeUuid")

val Any.codePath: String?
    get() = PackageParserRef.getStringValue(this, "codePath")

val Any.baseCodePath: String?
    get() = PackageParserRef.getStringValue(this, "baseCodePath")

val Any.splitCodePaths: Array<String>?
    get() = PackageParserRef.getStringArrayValue(this, "splitCodePaths")

val Any.baseRevisionCode: Int
    get() = PackageParserRef.getIntValue(this, "baseRevisionCode", -1)

val Any.splitRevisionCodes: IntArray?
    get() = PackageParserRef.getIntArrayValue(this, "splitRevisionCodes")

val Any.splitFlags: IntArray?
    get() = PackageParserRef.getIntArrayValue(this, "splitFlags")

val Any.splitPrivateFlags: IntArray?
    get() = PackageParserRef.getIntArrayValue(this, "splitPrivateFlags")

val Any.baseHardwareAccelerated: Boolean
    get() = PackageParserRef.getBooleanValue(this, "baseHardwareAccelerated", false)

/**
 * @return Package
 */
val Any.parentPackage: Any?
    get() = PackageParserRef.getObjectValue(this, "parentPackage")

/**
 * @return List<Package>
 */
val Any.childPackages: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "childPackages")

val Any.staticSharedLibName: String?
    get() = PackageParserRef.getStringValue(this, "staticSharedLibName")

val Any.staticSharedLibVersion: Int
    get() = PackageParserRef.getIntValue(this, "staticSharedLibVersion", 0)

val Any.usesStaticLibrariesVersions: IntArray?
    get() = PackageParserRef.getIntArrayValue(this, "usesStaticLibrariesVersions")

val Any.usesStaticLibrariesCertDigests: Array<String>?
    get() = PackageParserRef.getStringArrayValue(this, "usesStaticLibrariesCertDigests")

val Any.usesLibraryFiles: Array<String>?
    get() = PackageParserRef.getStringArrayValue(this, "usesLibraryFiles")

val Any.installLocation: Int
    get() = PackageParserRef.getIntValue(this, "installLocation", 0)

val Any.coreApp: Boolean
    get() = PackageParserRef.getBooleanValue(this, "coreApp", false)

val Any.cpuAbiOverride: String?
    get() = PackageParserRef.getStringValue(this, "cpuAbiOverride")

val Any.use32bitAbi: Boolean
    get() = PackageParserRef.getBooleanValue(this, "use32bitAbi", false)

val Any.restrictUpdateHash: ByteArray?
    get() = PackageParserRef.getByteArrayValue(this, "restrictUpdateHash")

val Any.visibleToInstantApps: Boolean
    get() = PackageParserRef.getBooleanValue(this, "visibleToInstantApps", false)

val Any.requestedPermissions: List<String>?
    get() = PackageParserRef.getStringListValue(this, "requestedPermissions")

val Any.protectedBroadcasts: List<String>?
    get() = PackageParserRef.getStringListValue(this, "protectedBroadcasts")

val Any.libraryNames: List<String>?
    get() = PackageParserRef.getStringListValue(this, "libraryNames")

val Any.usesLibraries: List<String>?
    get() = PackageParserRef.getStringListValue(this, "usesLibraries")

val Any.usesStaticLibraries: List<String>?
    get() = PackageParserRef.getStringListValue(this, "usesStaticLibraries")

val Any.usesOptionalLibraries: List<String>?
    get() = PackageParserRef.getStringListValue(this, "usesOptionalLibraries")

/**
 * @return List<Activity>
 */
val Any.activities: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "activities")

/**
 * @return List<Activity>
 */
val Any.receivers: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "receivers")

/**
 * @return List<Service>
 */
val Any.services: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "services")

/**
 * @return List<Provider>
 */
val Any.providers: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "providers")

/**
 * @return List<Permission>
 */
val Any.permissions: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "permissions")

/**
 * @return List<PermissionGroup>
 */
val Any.permissionGroups: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "permissionGroups")

/**
 * @return List<Instrumentation>
 */
val Any.instrumentation: List<Any>?
    get() = PackageParserRef.getObjectListValue(this, "instrumentation")

@Suppress("UNCHECKED_CAST")
val Any.reqFeatures: List<FeatureInfo>?
    get() = PackageParserRef.getObjectListValue(this, "reqFeatures") as List<FeatureInfo>?

@Suppress("UNCHECKED_CAST")
val Any.featureGroups: List<FeatureGroupInfo>?
    get() = PackageParserRef.getObjectListValue(this, "featureGroups") as List<FeatureGroupInfo>?

@Suppress("UNCHECKED_CAST")
val Any.configPreferences: List<ConfigurationInfo>?
    get() = PackageParserRef.getObjectListValue(this, "configPreferences") as List<ConfigurationInfo>?




/**
 * ******************************************
 * ******************************************
 * INNER CLASS ACTIVITY
 * ******************************************
 * ******************************************
 */

val Any.isActivity: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$Activity")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Throwable) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isActivity" equals true.
// ==============================================================

val Any.activityInfo: ActivityInfo?
    get() = PackageParserRef.getObjectValue(this, "info") as ActivityInfo?


/**
 * ******************************************
 * ******************************************
 * INNER CLASS SERVICE
 * ******************************************
 * ******************************************
 */

val Any.isService: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$Service")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Throwable) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isService" equals true.
// ==============================================================

val Any.serviceInfo: ServiceInfo?
    get() = PackageParserRef.getObjectValue(this, "info") as ServiceInfo?

/**
 * ******************************************
 * ******************************************
 * INNER CLASS PROVIDER
 * ******************************************
 * ******************************************
 */

val Any.isProvider: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$Provider")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Throwable) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isProvider" equals true.
// ==============================================================

val Any.providerInfo: ProviderInfo?
    get() = PackageParserRef.getObjectValue(this, "info") as ProviderInfo?


val Any.providerSyncable: Boolean
    get() = PackageParserRef.getBooleanValue(this, "syncable", false)


/**
 * ******************************************
 * ******************************************
 * INNER CLASS PERMISSION
 * ******************************************
 * ******************************************
 */

val Any.isPermission: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$Permission")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Throwable) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isService" equals true.
// ==============================================================

val Any.permissionInfo: PermissionInfo?
    get() = PackageParserRef.getObjectValue(this, "info") as PermissionInfo?


val Any.permissionTree: Boolean
    get() = PackageParserRef.getBooleanValue(this, "tree", false)

/**
 * @return PermissionGroup
 */
val Any.permissionGroup: Any?
    get() = PackageParserRef.getObjectValue(this, "group")

/**
 * ******************************************
 * ******************************************
 * INNER CLASS PERMISSIONGROUP
 * ******************************************
 * ******************************************
 */

val Any.isPermissionGroup: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$PermissionGroup")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Throwable) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isService" equals true.
// ==============================================================

val Any.permissionGroupInfo: PermissionGroupInfo?
    get() = PackageParserRef.getObjectValue(this, "info") as PermissionGroupInfo?

/**
 * ******************************************
 * ******************************************
 * INNER CLASS INSTRUMENTATION
 * ******************************************
 * ******************************************
 */

val Any.isInstrumentation: Boolean
    get() {
        var ret = false
        try {
            val cPackage = Class.forName("android.content.pm.PackageParser\$Instrumentation")
            val cThis = javaClass
            ret = cPackage == cThis
        } catch (e: Throwable) {

        }
        return ret
    }

// ==============================================================
// All methods here must be called when "isInstrumentation" equals true.
// ==============================================================

val Any.instrumentationInfo: InstrumentationInfo?
    get() = PackageParserRef.getObjectValue(this, "info") as InstrumentationInfo?

/**
 * ******************************************
 * ******************************************
 * INNER CLASS COMPONENT
 * ******************************************
 * ******************************************
 */

// ==============================================================
// All methods here must be called when "isActivity"
// or "isService" or "isProvider" or "isPermission"
// or "isPermissionGroup" or "isInstrumentation" equals true
// ==============================================================

/**
 * @return List<IntentFilter>
 */
@Suppress("UNCHECKED_CAST")
val Any.intents: List<IntentFilter>?
    get() = PackageParserRef.getObjectListValueSuper(this, "intents") as List<IntentFilter>?

val Any.className: String?
    get() = PackageParserRef.getStringValueSuper(this, "className")

val Any.metaData: Bundle?
    get() = PackageParserRef.getObjectValueSuper(this, "metaData") as Bundle?

/**
 * @return Package
 */
val Any.owner: Any?
    get() = PackageParserRef.getObjectValueSuper(this, "owner")

val Any.componentName: ComponentName?
    get() = PackageParserRef.getObjectValueSuper(this, "componentName") as ComponentName?

val Any.componentShortName: String?
    get() = PackageParserRef.getStringValueSuper(this, "componentShortName")
