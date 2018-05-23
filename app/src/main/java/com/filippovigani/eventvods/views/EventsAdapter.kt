package com.filippovigani.eventvods.views

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.binding.RecyclerViewAdapter
import com.filippovigani.eventvods.binding.RecyclerViewViewHolder
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.viewmodels.EventViewModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.view.*

class EventsAdapter(items: Collection<Event>? = null) : RecyclerViewAdapter<Event>(items) {

	var events: List<Event>?
		get() = this.items
		set(value) {
			if (value is ObservableList<Event>)
				this.items = value
			else
				ObservableArrayList<Event>().let { list ->
					value?.let { list.addAll(it) }
					this.items = list
				}
		}

	override fun getLayoutIdForPosition(position: Int) = R.layout.fragment_event

	override fun getViewModel(position: Int) = EventViewModel(items?.get(position) ?: Event(null))

	override fun getItemCount() = items?.size ?: 0

	override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
		val eventViewModel = getViewModel(position)
		holder.bind(eventViewModel)
		Picasso.get().load(eventViewModel.event.logo).into(holder.itemView.event_image)
	}

}