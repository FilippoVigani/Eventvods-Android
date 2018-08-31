package com.filippovigani.eventvods.services

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.models.Match
import com.filippovigani.eventvods.networking.Endpoint
import com.filippovigani.eventvods.networking.HttpsRequestTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.OkHttpClient
import okhttp3.Request


class EventvodsRepository {
	companion object {

		private inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
		private val gson = Gson()

		private val eventsMap : HashMap<String, Event> = HashMap()
		private val matchesMap : HashMap<String, Match> = HashMap()

		val events = PublishSubject.create<List<Event>>()

		private val httpClient = OkHttpClient()

		fun fetchEvents(){
			Observable.fromCallable { httpClient.newCall(Request.Builder().url(Endpoint.EVENTS.url).build()).execute() }
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.map { gson.fromJson<List<Event>>(it.body()?.string() ?: "") } //TODO: Consider using retrofit
				.doOnNext {it.forEach { event -> eventsMap[event.slug] = event } }
				//.subscribeBy ( onNext = {events.onNext(it)} )
				.subscribe(events)
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
				result.sections?.forEach { it -> it.modules?.forEach { it.matches?.forEach { matchesMap[it.id] = it} } }
				event.postValue(result)
			}.execute(Endpoint.EVENT.url(eventSlug))
			return event
		}

		fun getMatch(matchId: String) : LiveData<Match>{
			val match = MutableLiveData<Match>()
			matchesMap[matchId]?.let{match.postValue(it)}
			return match
		}
	}
}