package com.rarnu.base.app

import android.app.Fragment
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.rarnu.base.R
import com.rarnu.base.app.inner.IFragments
import com.rarnu.base.app.inner.InnerActivity
import com.rarnu.base.component.sliding.ISliding
import com.rarnu.base.component.sliding.SlidingHelper
import com.rarnu.base.component.sliding.SlidingMenu
import com.rarnu.base.utils.DrawableUtils
import com.rarnu.base.utils.UIUtils

/**
 * Created by rarnu on 3/24/16.
 */
abstract class BaseSlidingActivity: InnerActivity(), ISliding, IFragments {

    abstract fun replaceMenuFragment(): Fragment?

    abstract fun replaceSecondMenuFragment(): Fragment?

    abstract fun getBehindOffset(): Int

    abstract fun getAboveTouchMode(): Int

    abstract fun getBehindTouchMode(): Int

    abstract fun getSlideMode(): Int

    private var _helper: SlidingHelper? = null

    override fun getReplaceId(): Int = R.id.fReplacement

    override fun getBaseLayout(): Int = R.layout.layout_replacement

    override fun getCloseCondition(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        _helper = SlidingHelper(this)
        _helper?.onCreate()
        loadFragments()
        super.onCreate(savedInstanceState)
        setBehindContentView(R.layout.layout_menu_replacement)
        findViewById(R.id.menu)?.background = if (UIUtils.isFollowSystemBackground) {
            DrawableUtils.getSystemAttrDrawable(this, DrawableUtils.DETAILS_ELEMENT_BACKGROUND)
        } else { null }
        val sm = getSlidingMenu()
        sm!!.behindShadowWidth = 15
        sm.setBehindShadowDrawable(R.drawable.shadow)
        sm.behindOffset = getBehindOffset()
        sm.behindFadeDegree = 0.35f
        sm.touchModeAbove = getAboveTouchMode()
        sm.touchModeBehind = getBehindTouchMode()
        sm.mode = getSlideMode()
        if (sm.mode == SlidingMenu.LEFT || sm.mode == SlidingMenu.LEFT_RIGHT) {
            replaceMenu()
        }
        if (sm.mode == SlidingMenu.LEFT_RIGHT || sm.mode == SlidingMenu.RIGHT) {
            sm.setBehindSecondaryMenu(R.layout.layout_second_menu_replacement)
            findViewById(R.id.second_menu)?.background = if (UIUtils.isFollowSystemBackground) {
                DrawableUtils.getSystemAttrDrawable(this, DrawableUtils.DETAILS_ELEMENT_BACKGROUND)
            } else { null }
            sm.setBehindSecondaryShadowDrawable(R.drawable.shadow)
            replaceSecondMenu()
        }
    }

    override fun onDestroy() {
        releaseFragments()
        super.onDestroy()
    }

    fun replaceMenu() = fragmentManager.beginTransaction().replace(R.id.menu, replaceMenuFragment()).commit()

    fun replaceSecondMenu() = fragmentManager.beginTransaction().replace(R.id.second_menu, replaceSecondMenuFragment()).commit()

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        _helper?.onPostCreate(savedInstanceState)
    }

    override fun findViewById(id: Int): View? {
        val v = super.findViewById(id)
        if (v != null) {
            return v
        }
        return _helper?.findViewById(id)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        _helper?.onSaveInstanceState(outState)
    }

    override fun setContentView(layoutResID: Int) = setContentView(layoutInflater.inflate(layoutResID, null))

    override fun setContentView(view: View?) = setContentView(view, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        _helper?.registerAboveContentView(view)
    }

    override fun setBehindContentView(id: Int) = setBehindContentView(layoutInflater.inflate(id, null))

    override fun setBehindContentView(v: View?) = setBehindContentView(v, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

    override fun setBehindContentView(v: View?, params: ViewGroup.LayoutParams?) = _helper!!.setBehindContentView(v)

    override fun getSlidingMenu(): SlidingMenu? = _helper!!.slidingMenu

    override fun toggle() = _helper!!.toggle()

    override fun showContent() = _helper!!.showContent()

    override fun showMenu() = _helper!!.showMenu()

    override fun showSecondaryMenu() = _helper!!.showSecondaryMenu()

    override fun setSlidingActionBarEnabled(slidingActionBarEnabled: Boolean) = _helper!!.setSlidingActionBarEnabled(slidingActionBarEnabled)

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        var b = _helper!!.onKeyUp(keyCode)
        if (b) {
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                toggle()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}