package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository

class EventListViewModel : ViewModel() {
	lateinit var events: LiveData<ObservableList<Event>>
	val loading: MediatorLiveData<Boolean> = MediatorLiveData() //TODO change to data state

	init {
		loadEvents()
		loading.addSource(events, {loading.postValue(false)})
	}

	fun loadEvents(){
		loading.postValue(true)
		events = EventvodsRepository.fetchEvents()
	}

	var selected: Event? = null
}