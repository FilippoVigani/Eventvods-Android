package com.filippovigani.eventvods.views

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.viewmodels.EventViewModel

class EventsAdapter(items: Collection<Event>? = null) : RecyclerBaseAdapter<Event>(items) {

	var events: List<Event>?
		get() = this.items
		set(value) {
			ObservableArrayList<Event>().let { list ->
				value?.let { list.addAll(it) }
				this.items = list
			}
		}

	override fun getLayoutIdForPosition(position: Int) = R.layout.fragment_event

	override fun getViewModel(position: Int) = EventViewModel(items?.get(position) ?: Event("Empty event"))

	override fun getItemCount() = items?.size ?: 0
}