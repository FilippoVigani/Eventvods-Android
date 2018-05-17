package com.filippovigani.eventvods.networking

import android.util.Log
import com.filippovigani.eventvods.models.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EventvodsApi {
	companion object {

		inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
		val gson = Gson()

		fun getEvents(callback: (List<Event>) -> Unit){
			HttpsRequestTask({response -> run {
			}
				val events = gson.fromJson<List<Event>>(response)
				callback(events)
			}).execute(Endpoint.EVENTS.url)
		}
	}
}