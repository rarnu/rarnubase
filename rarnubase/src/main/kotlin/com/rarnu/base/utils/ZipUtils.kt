package com.rarnu.base.utils

import android.util.Log
import kotlin.concurrent.thread

/**
 * Created by rarnu on 6/8/17.
 */
object ZipUtils {

    val ERRORCODE_NOERROR = 0
    val ERRORCODE_FORMAT_NOT_SUPPORT = -1
    val ERRORCODE_UNCOMPRESS = -2
    val ERRORCODE_COMPRESS = -3

    init {
        try {
            System.loadLibrary("compress")
        } catch(e: Exception) {
            Log.e("ZipUtils", "System.loadLibrary(\"compress\") => ${e.message}")
        }
    }

    external fun uncompress(filePath: String, dest: String): Int
    external fun compress(filePath: String, src: String): Int
    external fun getFileSize(path: String): String

    external fun getCompressErrorCode(filePath: String): Int
    external fun getCompressErrorMessage(filePath: String): String
    external fun getCompressFileCount(filePath: String): Int

    external fun getCompressedCount(filePath: String): Int
    external fun getUncompressErrorCode(filePath: String): Int
    external fun getUncompressErrorMessage(filePath: String): String
    external fun getUncompressFileCount(filePath: String): Int
    external fun getUncompressedCount(filePath: String): Int

    data class CompressStatus(var filePath: String?, var fileCount: Int, var compressCount: Int, var errCode: Int, var errMsg: String?)
    data class UncompressStatus(var filePath: String?, var fileCount: Int, var uncompressCount: Int, var errCode: Int, var errMsg: String?)
    fun getCompressStatus(filePath: String): CompressStatus = CompressStatus(filePath, getCompressFileCount(filePath), getCompressedCount(filePath), getCompressErrorCode(filePath), getCompressErrorMessage(filePath))
    fun getUncompressStatus(filePath: String): UncompressStatus = UncompressStatus(filePath, getUncompressFileCount(filePath), getUncompressedCount(filePath), getUncompressErrorCode(filePath), getUncompressErrorMessage(filePath))

}

class Zip {
    var archive = ""
    var src = ""
    internal var _status: (ZipUtils.CompressStatus?) -> Unit = {}
    fun onStatus(status: (ZipUtils.CompressStatus?) -> Unit) {
        _status = status
    }
}

class Unzip {
    var archive = ""
    var dest = ""
    internal var _status: (ZipUtils.UncompressStatus?) -> Unit = {}
    fun onStatus(status: (ZipUtils.UncompressStatus?) -> Unit) {
        _status = status
    }
}

fun zipAsync(init: Zip.() -> Unit) = thread { zip(init) }

fun zip(init: Zip.() -> Unit) {
    val z = Zip()
    z.init()
    ZipUtils.compress(z.archive, z.src)
    z._status(ZipUtils.getCompressStatus(z.archive))
}

fun unzipAsync(init: Unzip.() -> Unit) = thread { unzip(init) }

fun unzip(init: Unzip.() -> Unit) {
    val z = Unzip()
    z.init()
    ZipUtils.uncompress(z.archive, z.dest)
    z._status(ZipUtils.getUncompressStatus(z.archive))
}