package com.rarnu.base.app

import com.rarnu.base.app.inner.InnerFragment

/**
 * Created by rarnu on 3/23/16.
 */
abstract class BaseFragment: InnerFragment {

    constructor(): super()

    constructor(tabTitle: String): super(tabTitle)

}