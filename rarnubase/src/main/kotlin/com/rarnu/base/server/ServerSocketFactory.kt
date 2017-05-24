package com.rarnu.base.server

import java.net.ServerSocket

/**
 * Created by rarnu on 4/27/16.
 */
interface ServerSocketFactory {
    fun create(): ServerSocket?
}