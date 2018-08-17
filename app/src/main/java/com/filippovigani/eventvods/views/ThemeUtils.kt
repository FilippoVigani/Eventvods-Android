package com.filippovigani.eventvods.views

import android.content.Context
import android.content.res.Resources
import com.filippovigani.eventvods.R
import android.util.TypedValue



object ThemeUtils {
	fun getGameTheme(gameSlug: String?) : Int{
		return when(gameSlug){
			"dota" -> R.style.AppTheme_dota
			"lol" -> R.style.AppTheme_lol
			"csgo" -> R.style.AppTheme_csgo
			"rocket-league" -> R.style.AppTheme_rocketleague
			"overwatch" -> R.style.AppTheme_overwatch
			"pubg" -> R.style.AppTheme_NoActionBar
			else -> R.style.AppTheme_NoActionBar
		}
	}
}

fun Resources.Theme.getAttribute(attributeId: Int): Int {
	val typedValue = TypedValue()
	this.resolveAttribute(attributeId, typedValue, true)
	return typedValue.resourceId
}