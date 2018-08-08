package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.filippovigani.eventvods.models.Event

class EventSectionViewModel(val section: Event.Section) : ViewModel() {
	class Factory(private val section:  Event.Section) : ViewModelProvider.NewInstanceFactory() {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return EventSectionViewModel(section) as T
		}
	}
}