package com.filippovigani.eventvods.views

import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.models.Game

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