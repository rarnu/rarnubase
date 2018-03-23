package com.rarnu.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rarnu.base.app.common.Actions
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by rarnu on 3/27/16.
 */
object DownloadUtils {

    private val listDownloading = arrayListOf<DownloadInfo>()

    fun stopDownloadTask(localDir: String, localFile: String) {
        val filePath = localDir + localFile
        for (di in listDownloading) {
            if (di.fileName == filePath) {
                di.thread?.interrupt()
                listDownloading.remove(di)
                break
            }
        }
    }

    fun downloadFileT(
            activity: Activity,
            url: String,
            localDir: String,
            localFile: String,
            iv: ImageView? = null,
            bop: BitmapFactory.Options? = null,
            isRound: Boolean = false,
            radis: Int = 0,
            handle: ((Int, Int, Int, String?) -> Unit)?) {
        var nlocalDir = localDir
        if (!nlocalDir.endsWith("/")) {
            nlocalDir += "/"
        }
        val fDir = File(nlocalDir)
        if (!fDir.exists()) {
            fDir.mkdirs()
        }
        val filePath = nlocalDir + localFile

        val fImg = File(filePath)
        if (fImg.exists()) {
            if (iv != null) {
                var bmp = if (bop != null) {
                    BitmapFactory.decodeFile(filePath, bop)
                } else {
                    BitmapFactory.decodeFile(filePath)
                }
                if (isRound) {
                    bmp = ImageUtils.roundedCornerBitmap(bmp, radis.toFloat())
                }
                iv.setImageBitmap(bmp)
            } else if (handle != null) {
                handle(Actions.WHAT_DOWNLOAD_FINISH, 0, 0, null)
            }
            return
        }

        val tDownload = Thread {
            downloadFile(url, filePath, handle, null)
            activity.runOnUiThread {
                val file = File(filePath)
                if (file.exists()) {
                    if (iv != null) {
                        var bmp = if (bop != null) {
                            BitmapFactory.decodeFile(filePath, bop)
                        } else {
                            BitmapFactory.decodeFile(filePath)
                        }
                        if (isRound) {
                            bmp = ImageUtils.roundedCornerBitmap(bmp, radis.toFloat())
                        }
                        iv.setImageBitmap(bmp)
                    }
                }
                for (di in listDownloading) {
                    if (di.fileName == filePath) {
                        listDownloading.remove(di)
                        break
                    }
                }
            }
        }

        val info = DownloadInfo()
        info.fileName = filePath
        info.thread = tDownload
        val hasTask = listDownloading.any { it.fileName == info.fileName }
        if (!hasTask) {
            listDownloading.add(info)
            tDownload.start()
        }
    }

    fun downloadFileT(activity: Activity, url: String, localDir: String, localFile: String, iv: ImageView?, tv: TextView?) =
            downloadFileT(activity, url, localDir, localFile, iv = iv) { status, position, fileSize, _ ->
                activity.runOnUiThread {
                    when (status) {
                        Actions.WHAT_DOWNLOAD_START -> {
                            tv?.visibility = View.VISIBLE
                            tv?.text = "$position / $fileSize"
                        }
                        Actions.WHAT_DOWNLOAD_PROGRESS -> {
                            tv?.text = "$position / $fileSize"
                        }
                        Actions.WHAT_DOWNLOAD_FINISH -> {
                            tv?.visibility = View.GONE
                        }
                    }
                }
            }


    fun downloadFile(address: String, localFile: String, handle: ((Int, Int, Int, String?) -> Unit)?, callback: BreakableThread.RunningCallback?) {
        val fTmp = File(localFile)
        if (fTmp.exists()) {
            fTmp.delete()
        }
        var isDownloadNormal = true
        val url: URL?
        var filesize = 0
        var position = 0
        try {
            url = URL(address)
            val con = url.openConnection() as HttpURLConnection
            con.connectTimeout = 5000
            con.readTimeout = 5000
            val ins = con.inputStream
            filesize = con.contentLength
            handle?.invoke(Actions.WHAT_DOWNLOAD_START, position, filesize, null)
            val fileOut = File(localFile + ".tmp")
            val out = FileOutputStream(fileOut)
            val buffer = ByteArray(1024)
            var count: Int
            while (true) {
                count = ins.read(buffer)
                if (count != -1) {
                    out.write(buffer, 0, count)
                    position += count
                    handle?.invoke(Actions.WHAT_DOWNLOAD_PROGRESS, position, filesize, null)
                    if (callback != null) {
                        if (!callback.getRunningState()) {
                            isDownloadNormal = false
                            break
                        }
                    }
                } else {
                    break
                }
            }
            ins.close()
            out.close()
            fileOut.renameTo(fTmp)
            if (!isDownloadNormal) {
                fileOut.delete()
                fTmp.delete()
            }
            handle?.invoke(Actions.WHAT_DOWNLOAD_FINISH, 0, filesize, null)
        } catch (e: Exception) {
            handle?.invoke(Actions.WHAT_DOWNLOAD_FINISH, 0, filesize, e.message)
        }

    }

    class DownloadInfo {
        var fileName: String? = null
        var thread: Thread? = null
    }

    class BreakableThread(callback: RunningCallback?) : Thread() {

        interface RunningCallback {
            fun getRunningState(): Boolean
        }

        private var _runningCallback: RunningCallback? = callback
        var runningCallback: RunningCallback?
            get() = _runningCallback
            set(value) {
                _runningCallback = value
            }
    }
}

class Download {
    var imageView: ImageView? = null
    var textView: TextView? = null
    var url = ""
    var localDir = ""
    var localFile = ""
    var bitmapOption: BitmapFactory.Options? = null
    var isRoundCorner = false
    var radis = 0
    internal var _progress: ((Int, Int, Int, String?) -> Unit) = { _,_,_,_ -> }
    fun progress(p:(Int, Int, Int, String?) -> Unit) {
        _progress = p
    }
}

class DownloadLite {
    var url = ""
    var localFile = ""
    internal var _progress: ((Int, Int, Int, String?) -> Unit)? = null
    fun progress(p: (Int, Int, Int, String?) -> Unit) {
        _progress = p
    }
}

fun download(init: DownloadLite.() -> Unit) {
    val d = DownloadLite()
    d.init()
    DownloadUtils.downloadFile(d.url, d.localFile, d._progress, null)
}

fun downloadAsync(activity: Activity, init: Download.() -> Unit) {
    val d = Download()
    d.init()
    if (d.textView != null) {
        DownloadUtils.downloadFileT(activity, d.url, d.localDir, d.localFile, d.imageView, d.textView)
    } else {
        DownloadUtils.downloadFileT(activity, d.url, d.localDir, d.localFile, d.imageView, d.bitmapOption, d.isRoundCorner, d.radis, d._progress)
    }
}