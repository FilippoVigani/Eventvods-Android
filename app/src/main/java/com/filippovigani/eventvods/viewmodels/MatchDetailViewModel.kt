package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.models.Match
import com.filippovigani.eventvods.services.EventvodsRepository

class MatchDetailViewModel(matchId : String) : ViewModel(){

	val match: LiveData<Match> = EventvodsRepository.getMatch(matchId)

	class Factory(private val matchId: String) : ViewModelProvider.NewInstanceFactory() {

		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return MatchDetailViewModel(matchId) as T
		}
	}
}