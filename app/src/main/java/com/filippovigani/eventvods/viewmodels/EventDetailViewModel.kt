package com.filippovigani.eventvods.viewmodels

import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository
import android.app.Application
import android.arch.lifecycle.*
import android.support.annotation.NonNull



class EventDetailViewModel(private val eventSlug: String) : ViewModel() {

	lateinit var event: LiveData<Event>
	val loading: MediatorLiveData<Boolean> = MediatorLiveData()

	init {
		loadEvent()
	}

	fun loadEvent(forceFetch: Boolean = false){
		if (::event.isInitialized) loading.removeSource(event)
		loading.postValue(true)
		event = if(forceFetch) EventvodsRepository.fetchEvent(eventSlug) else EventvodsRepository.getEvent(eventSlug)
		loading.addSource(event) { it -> if (it?.complete == true) loading.postValue(false)}
	}

	class Factory(private val eventSlug: String) : ViewModelProvider.NewInstanceFactory() {

		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return EventDetailViewModel(eventSlug) as T
		}
	}
}