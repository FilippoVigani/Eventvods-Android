package com.filippovigani.eventvods.views

import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.viewmodels.EventViewModel

class EventsAdapter(
		//private val context: Context,
		private val data: List<Event>?
) : RecyclerBaseAdapter() {
	override fun getLayoutIdForPosition(position: Int) = R.layout.fragment_event

	override fun getViewModel(position: Int) = EventViewModel(data?.get(position) ?: Event("Empty event"))

	override fun getItemCount() = data?.size ?: 0
}