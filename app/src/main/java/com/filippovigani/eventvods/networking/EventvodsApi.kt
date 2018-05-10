package com.filippovigani.eventvods.networking

import android.util.Log

class EventvodsApi {
	companion object {
		fun getEvents(){
			HttpsRequestTask({response -> Log.d("TAG", response)}).execute(Endpoint.EVENTS.url)
		}
	}
}