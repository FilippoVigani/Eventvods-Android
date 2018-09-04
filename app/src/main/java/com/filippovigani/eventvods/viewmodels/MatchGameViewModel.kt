package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.filippovigani.eventvods.models.Match

class MatchGameViewModel(val match: Match, val game: Match.Game) : ViewModel() {

	val highlightsUrl: String? = game.links?.getOrNull(match.highlightsIndex)

	class Factory(private val match: Match, private val game: Match.Game) : ViewModelProvider.NewInstanceFactory() {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return MatchGameViewModel(match, game) as T
		}
	}
}