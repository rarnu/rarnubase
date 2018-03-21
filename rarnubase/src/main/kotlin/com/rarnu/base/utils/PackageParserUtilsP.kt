package com.rarnu.base.utils

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.TypedArray


class PackageParserUtilsP {

    companion object {
        private val PROPERTY_CHILD_PACKAGES_ENABLED = "persist.sys.child_packages_enabled"
        private val MULTI_PACKAGE_APK_ENABLED = true
        private val MAX_PACKAGES_PER_APK = 5
        val APK_SIGNING_UNKNOWN = 0
        val APK_SIGNING_V1 = 1
        val APK_SIGNING_V2 = 2
        private val DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f
        private val ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml"
        private val MNT_EXPAND = "/mnt/expand/"
        private val TAG_MANIFEST = "manifest"
        private val TAG_APPLICATION = "application"
        private val TAG_PACKAGE_VERIFIER = "package-verifier"
        private val TAG_OVERLAY = "overlay"
        private val TAG_KEY_SETS = "key-sets"
        private val TAG_PERMISSION_GROUP = "permission-group"
        private val TAG_PERMISSION = "permission"
        private val TAG_PERMISSION_TREE = "permission-tree"
        private val TAG_USES_PERMISSION = "uses-permission"
        private val TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m"
        private val TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23"
        private val TAG_USES_CONFIGURATION = "uses-configuration"
        private val TAG_USES_FEATURE = "uses-feature"
        private val TAG_FEATURE_GROUP = "feature-group"
        private val TAG_USES_SDK = "uses-sdk"
        private val TAG_SUPPORT_SCREENS = "supports-screens"
        private val TAG_PROTECTED_BROADCAST = "protected-broadcast"
        private val TAG_INSTRUMENTATION = "instrumentation"
        private val TAG_ORIGINAL_PACKAGE = "original-package"
        private val TAG_ADOPT_PERMISSIONS = "adopt-permissions"
        private val TAG_USES_GL_TEXTURE = "uses-gl-texture"
        private val TAG_COMPATIBLE_SCREENS = "compatible-screens"
        private val TAG_SUPPORTS_INPUT = "supports-input"
        private val TAG_EAT_COMMENT = "eat-comment"
        private val TAG_PACKAGE = "package"
        private val TAG_RESTRICT_UPDATE = "restrict-update"
        private val TAG_USES_SPLIT = "uses-split"
        private val META_DATA_INSTANT_APPS = "instantapps.clients.allowed"
        private val METADATA_MAX_ASPECT_RATIO = "android.max_aspect"
        private val RECREATE_ON_CONFIG_CHANGES_MASK = ActivityInfo.CONFIG_MCC or ActivityInfo.CONFIG_MNC
        private val CHILD_PACKAGE_TAGS = setOf(
                TAG_APPLICATION,
                TAG_USES_PERMISSION,
                TAG_USES_PERMISSION_SDK_M,
                TAG_USES_PERMISSION_SDK_23,
                TAG_USES_CONFIGURATION,
                TAG_USES_FEATURE,
                TAG_FEATURE_GROUP,
                TAG_USES_SDK,
                TAG_SUPPORT_SCREENS,
                TAG_INSTRUMENTATION,
                TAG_USES_GL_TEXTURE,
                TAG_COMPATIBLE_SCREENS,
                TAG_SUPPORTS_INPUT,
                TAG_EAT_COMMENT
        )

        private val LOG_UNSAFE_BROADCASTS = false
        private val SAFE_BROADCASTS = setOf(Intent.ACTION_BOOT_COMPLETED)
        val NEW_PERMISSIONS = arrayOf(
                NewPermissionInfo(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.os.Build.VERSION_CODES.DONUT, 0),
                NewPermissionInfo(android.Manifest.permission.READ_PHONE_STATE, android.os.Build.VERSION_CODES.DONUT, 0)
        )
        val SPLIT_PERMISSIONS = arrayOf(
                SplitPermissionInfo(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), android.os.Build.VERSION_CODES.CUR_DEVELOPMENT + 1),
                SplitPermissionInfo(android.Manifest.permission.READ_CONTACTS, arrayOf(android.Manifest.permission.READ_CALL_LOG), android.os.Build.VERSION_CODES.JELLY_BEAN),
                SplitPermissionInfo(android.Manifest.permission.WRITE_CONTACTS, arrayOf(android.Manifest.permission.WRITE_CALL_LOG), android.os.Build.VERSION_CODES.JELLY_BEAN)
        )
    }

    data class NewPermissionInfo(var name: String?, var sdkVersion: Int, var fileVersion: Int)
    data class SplitPermissionInfo(var rootPerm: String?, var newPerms: Array<String>?, var targetSdk: Int)

    internal class ParsePackageItemArgs(val owner: Package, val outError: Array<String>,
                                        val nameRes: Int, val labelRes: Int, val iconRes: Int, val roundIconRes: Int, val logoRes: Int,
                                        val bannerRes: Int) {

        var tag: String? = null
        var sa: TypedArray? = null
    }

}