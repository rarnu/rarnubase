package com.rarnu.base.app

import android.content.Context
import com.rarnu.base.app.inner.InnerAdapter

/**
 * Created by rarnu on 3/23/16.
 */
abstract class BaseAdapter<T>(context: Context, list: MutableList<T>?) : InnerAdapter<T>(context, list)