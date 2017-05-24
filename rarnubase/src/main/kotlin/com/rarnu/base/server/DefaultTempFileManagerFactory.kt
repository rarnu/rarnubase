package com.rarnu.base.server

/**
 * Created by rarnu on 4/27/16.
 */
class DefaultTempFileManagerFactory: TempFileManagerFactory {
    override fun create(): TempFileManager? = DefaultTempFileManager()
}