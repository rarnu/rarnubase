package com.rarnu.base.server

/**
 * Created by rarnu on 4/27/16.
 */
interface TempFileManagerFactory {
    fun create(): TempFileManager?
}