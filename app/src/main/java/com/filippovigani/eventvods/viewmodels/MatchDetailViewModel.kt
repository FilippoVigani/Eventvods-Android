package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableBoolean
import com.filippovigani.eventvods.models.Match
import com.filippovigani.eventvods.services.EventvodsRepository

class MatchDetailViewModel(matchId : String) : ViewModel(){

	val match: LiveData<Match> = EventvodsRepository.getMatch(matchId)

	var currentVOD: Match.Game.VOD? = null
	var playbackTime: Float = 0f
	val isPlaying: ObservableBoolean = ObservableBoolean(false)

	class Factory(private val matchId: String) : ViewModelProvider.NewInstanceFactory() {

		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return MatchDetailViewModel(matchId) as T
		}
	}
}