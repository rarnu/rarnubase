package com.rarnu.base.app

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rarnu.base.app.inner.IIntf

/**
 * Created by rarnu on 3/23/16.
 */
abstract class BaseDialogFragment: Fragment(), IIntf {

    private var _innerView: View? = null
    protected var innerView: View
        get() = _innerView!!
        set(value) {
            _innerView = value
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        innerView = inflater.inflate(getFragmentLayoutResId(), container, false)
        initComponents()
        initEvents()
        initLogic()
        return innerView
    }

}