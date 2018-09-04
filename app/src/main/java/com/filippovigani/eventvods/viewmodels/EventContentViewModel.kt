package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.ViewModel
import com.filippovigani.eventvods.models.Event
import java.text.SimpleDateFormat

class EventContentViewModel(val event: Event) : ViewModel() {

	val eventPeriod: String
		get(){
			val formatter = SimpleDateFormat.getDateInstance()
			return " ${formatter.format(event.startDate)} - ${formatter.format(event.endDate)}"
		}
}