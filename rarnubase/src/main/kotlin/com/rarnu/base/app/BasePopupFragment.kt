package com.rarnu.base.app

import com.rarnu.base.app.inner.InnerFragment

/**
 * Created by rarnu on 3/25/16.
 */
abstract class BasePopupFragment: InnerFragment {

    constructor(): super()

    constructor(tabTitle: String): super(tabTitle)

}