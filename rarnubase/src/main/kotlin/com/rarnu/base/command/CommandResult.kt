package com.rarnu.base.command

import java.io.Serializable

/**
 * Created by rarnu on 3/23/16.
 */
data class CommandResult(var cmd: Command, var result: String, var error: String): Serializable