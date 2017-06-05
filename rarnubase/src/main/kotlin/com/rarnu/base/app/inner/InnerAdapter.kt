package com.rarnu.base.app.inner

import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable

/**
 * Created by rarnu on 3/23/16.
 */
@Suppress("UNCHECKED_CAST")
abstract class InnerAdapter<T, H> : BaseAdapter, Filterable {

    protected val lock = Any()
    protected var context: Context
    protected var inflater: LayoutInflater
    protected var listFull: MutableList<T>?
    protected var list: MutableList<T>?
    private  var _filter: ArrayFilter? = null
    protected var fileter: ArrayFilter? = null
        get() {
            if (_filter == null) {
                _filter = ArrayFilter()
            }
            return _filter
        }
    protected var pm: PackageManager

    constructor(context: Context, list: MutableList<T>?): super() {
        this.context = context
        this.inflater = LayoutInflater.from(context)
        this.listFull = list
        this.list = list
        this.pm = context.packageManager
    }

    open fun setNewList(list: MutableList<T>?) {
        this.listFull = list
        this.list = list
        notifyDataSetChanged()
    }

    open fun deleteItem(item: T) {
        list?.remove(item)
        listFull?.remove(item)
        notifyDataSetChanged()
    }

    open fun deleteItems(items: MutableList<T>) {
        for (i in items) {
            list?.remove(i)
            listFull?.remove(i)
        }
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any? = list!![position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list!!.size

    override fun getFilter(): Filter? = filter

    abstract fun getValueText(item: T): String?

    abstract fun getAdapterLayout(): Int

    abstract fun newHolder(baseView: View): H

    abstract fun fillHolder(baseVew: View, holder: H, item: T)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = convertView
        if (v == null) {
            v = inflater.inflate(getAdapterLayout(), parent, false)
        }
        var holder = v?.tag as H?
        if (holder == null) {
            holder = newHolder(v!!)
            v.tag = holder
        }
        val item = list!![position]
        fillHolder(v!!, holder!!, item)
        return v
    }

    open fun filter(text: String?) {
        fileter?.filter(text)
    }

    inner class ArrayFilter: Filter() {
        override fun performFiltering(prefix: CharSequence?): FilterResults? {
            list = listFull
            val results = FilterResults()
            if (prefix == null || prefix.length == 0) {
                synchronized (lock) {
                    val l = list
                    results.values = l
                    results.count = l!!.size
                }
            } else {
                val prefixString = prefix.toString().toLowerCase()
                val values = list
                val count = values!!.size
                val newValues = arrayListOf<T>()

                for (i in 0..count - 1) {
                    val value = values[i]
                    val valueText = getValueText(value)
                    if (valueText?.indexOf(prefixString) != -1) {
                        newValues.add(value)
                    }
                }
                results.values = newValues
                results.count = newValues.size
            }

            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null) {
                list = results.values as MutableList<T>?
                if (results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }

    }
}