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
		private val gson = Gson()

		private val events : MutableLiveData<List<Event>> = MutableLiveData()
		private val eventsMap : HashMap<String, Event> = HashMap()

		fun fetchEvents() : LiveData<List<Event>>{
			//TODO: Consider using List instead of ObservableList
			HttpsRequestTask { response ->
				val results = gson.fromJson<List<Event>>(response)
				results.forEach { event -> eventsMap[event.slug] = event }
				events.postValue(results)
			}.execute(Endpoint.EVENTS.url)
			return events
		}

		fun getEvent(eventSlug: String) : LiveData<Event>{
			val event = MediatorLiveData<Event>()
			event.postValue(eventsMap[eventSlug])
			if (eventsMap[eventSlug]?.complete == false){
				event.addSource(fetchEvent(eventSlug)) { event.postValue(it) }
			}
			return event
		}

		fun fetchEvent(eventSlug: String) : LiveData<Event>{
			val event = MutableLiveData<Event>()
			HttpsRequestTask { response ->
				val result = gson.fromJson<Event>(response)
				eventsMap[result.slug] = result
				result.complete = true
				event.postValue(result)
			}.execute(Endpoint.EVENT.url(eventSlug))
			return event
		}
	}
}