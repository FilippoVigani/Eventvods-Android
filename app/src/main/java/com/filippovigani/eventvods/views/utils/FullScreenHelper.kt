package com.filippovigani.eventvods.views.utils

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.*
import android.view.ViewGroup


class FullScreenHelper/**
 * @param context
 * @param views to hide/show
 */
(private val context:AppCompatActivity, vararg views:View) {
	private val views:Array<out View> = views

	/**
	 * call this method to enter full screen
	 */
	fun enterFullScreen() {
		val decorView = context.window.decorView

		hideSystemUI(decorView)

		for (view in views)
		{
			view.visibility = View.GONE
			view.invalidate()
		}
	}

	/**
	 * call this method to exit full screen
	 */
	fun exitFullScreen() {
		val decorView = context.window.decorView

		showSystemUI(decorView)

		for (view in views)
		{
			view.visibility = View.VISIBLE
			view.invalidate()
		}
	}

	var fitsSystemWindows = true
	var systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE

	private fun hideSystemUI(mDecorView:View) {
		val container = context.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
		fitsSystemWindows = container?.fitsSystemWindows ?: true
		container?.fitsSystemWindows = false
		systemUiVisibility = mDecorView.systemUiVisibility
		context.supportActionBar?.hide()
		mDecorView.systemUiVisibility = (
				SYSTEM_UI_FLAG_HIDE_NAVIGATION
				or SYSTEM_UI_FLAG_FULLSCREEN
				or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				)
	}

	private fun showSystemUI(mDecorView:View) {
		val container = context.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
		container?.fitsSystemWindows = fitsSystemWindows
		context.supportActionBar?.show()
		mDecorView.systemUiVisibility = systemUiVisibility
	}
}