package com.filippovigani.eventvods.services

import android.arch.lifecycle.LiveData
import com.filippovigani.eventvods.models.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableList
import com.filippovigani.eventvods.networking.Endpoint
import com.filippovigani.eventvods.networking.HttpsRequestTask
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.databinding.ObservableArrayList


class EventvodsRepository {
	companion object {

		inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
		val gson = Gson()

		val events : MutableLiveData<ObservableList<Event>> = MutableLiveData()

		fun fetchEvents() : LiveData<ObservableList<Event>>{
			//TODO: Consider using List instead of ObservableList
			HttpsRequestTask({ response ->
				val observableEvents = ObservableArrayList<Event>()
				observableEvents.addAll(gson.fromJson<List<Event>>(response))
				events.postValue(observableEvents)
			}).execute(Endpoint.EVENTS.url)
			return events
		}

		fun getEvent(eventId: String) : LiveData<Event>{
			//TODO: Adopt a proper cache
			val event = MediatorLiveData<Event>()
			event.addSource(events, { events ->
				//TODO: Use an observable hashmap
				events?.find { event -> event.id == eventId }?.let {
					event.postValue(it)
				}
			})
			return event
		}
	}
}