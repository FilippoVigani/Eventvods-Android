package com.filippovigani.eventvods.viewmodels

import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository
import android.app.Application
import android.arch.lifecycle.*
import android.support.annotation.NonNull



class EventDetailViewModel(private val eventSlug: String) : ViewModel() {

	var event: LiveData<Event> = EventvodsRepository.getEvent(eventSlug)
	val loading: MediatorLiveData<Boolean> = MediatorLiveData()

	init {
		loading.addSource(event) {loading.postValue(false)}
		loading.postValue(true)
	}

	fun reloadEvent(){
		loading.removeSource(event)
		loading.postValue(true)
		event = EventvodsRepository.fetchEvent(eventSlug)
		loading.addSource(event) {loading.postValue(false)}
	}

	class Factory(private val eventSlug: String) : ViewModelProvider.NewInstanceFactory() {

		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return EventDetailViewModel(eventSlug) as T
		}
	}
}