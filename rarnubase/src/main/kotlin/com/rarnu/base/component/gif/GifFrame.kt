package com.rarnu.base.component.gif

import android.graphics.Bitmap

/**
 * Created by rarnu on 3/29/16.
 */
class GifFrame {

    var image: Bitmap? = null
    var delay = 0
    var imageName: String? = null
    var nextFrame: GifFrame? = null

    constructor(bmp: Bitmap?, del: Int) {
        image = bmp
        delay = del
    }

    constructor(name: String, del: Int) {
        imageName = name
        delay = del
    }

}