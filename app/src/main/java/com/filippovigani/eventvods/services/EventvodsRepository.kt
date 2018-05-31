package com.filippovigani.eventvods.services

import android.arch.lifecycle.LiveData
import com.filippovigani.eventvods.models.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.arch.lifecycle.MutableLiveData
import com.filippovigani.eventvods.networking.Endpoint
import com.filippovigani.eventvods.networking.HttpsRequestTask


class EventvodsRepository {
	companion object {

		inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
		val gson = Gson()

		var events : List<Event>? = null

		fun getEvents(callback: (List<Event>) -> Unit){
			//TODO change to LiveData
			HttpsRequestTask({ response ->
				run {
				}
				val events = gson.fromJson<List<Event>>(response)
				Companion.events = events
				callback(events)
			}).execute(Endpoint.EVENTS.url)
		}

		fun getEvent(eventId: String) : LiveData<Event>{
			//TODO Adopt a proper cache
			/*val cached = eventsCache.get(eventId)
			if (cached != null) {
				return cached
			}*/
			val data = MutableLiveData<Event>()

			//TODO I mean...
			events?.find { event -> event.id == eventId }?.let {
				data.postValue(it)
				return data
			}

			getEvents { events ->
				events?.find { event -> event.id == eventId }?.let {
					data.postValue(it)
				}
			}

			return data
		}
	}
}