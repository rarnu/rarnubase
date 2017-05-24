package com.rarnu.base.server

/**
 * Created by rarnu on 4/27/16.
 */
interface AsyncRunner {

    fun closeAll()
    fun closed(clientHandler: ClientHandler?)
    fun exec(code: ClientHandler?)

}