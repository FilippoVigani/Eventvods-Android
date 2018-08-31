package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.*
import android.util.Log
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

class EventListViewModel : ViewModel() {
	var events: MutableLiveData<List<Event>> = MutableLiveData()
	val loading: MutableLiveData<Boolean> = MutableLiveData() //TODO change to data state

	init {
		EventvodsRepository.events
				.subscribeBy(
						onNext = {
							events.postValue(it)
							loading.postValue(false)
						},
						onError =  {
							it.printStackTrace()
							loading.postValue(false)
						},
						onComplete = { Log.d("STATUS", "completed") }
				)
		loadEvents()
	}

	fun loadEvents(){
		loading.postValue(true)
		EventvodsRepository.fetchEvents()
	}

	var selected: Event? = null
}