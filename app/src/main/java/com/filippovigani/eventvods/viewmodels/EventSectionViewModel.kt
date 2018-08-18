package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.filippovigani.eventvods.models.Event

class EventSectionViewModel(private val section: Event.Section) : ViewModel() {

	val matches: List<Any>
		get(){
			val matches = ArrayList<Any>()
			section.modules?.forEach { module -> run {
				matches.add(module) //Add module for header
				module.matches?.apply { matches.addAll(this) } //Add matches for content
			} }
			return matches
		}

	class Factory(private val section:  Event.Section) : ViewModelProvider.NewInstanceFactory() {
		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return EventSectionViewModel(section) as T
		}
	}
}