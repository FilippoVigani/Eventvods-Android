package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository


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