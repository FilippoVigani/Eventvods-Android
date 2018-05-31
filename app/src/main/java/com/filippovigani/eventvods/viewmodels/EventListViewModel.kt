package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository

class EventListViewModel : ViewModel() {
	val events: MutableLiveData<ObservableList<Event>> = MutableLiveData()
	val loading: MutableLiveData<Boolean> = MutableLiveData() //TODO change to data state

	init {
		loadEvents()
	}

	fun loadEvents(){
		loading.postValue(true)
		EventvodsRepository.getEvents {
			response ->
			ObservableArrayList<Event>().let { list ->
				list.addAll(response)
				loading.postValue(false)
				events.postValue(list)
			}
		}
	}

	var selected: Event? = null
}