package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.services.EventvodsRepository

class EventDetailViewModel(val eventId: String) : ViewModel() {

	var event: LiveData<Event> = EventvodsRepository.getEvent(eventId)
}