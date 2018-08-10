package com.filippovigani.eventvods.views.adapters

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.view.View
import com.filippovigani.eventvods.views.EventDetailActivity
import com.filippovigani.eventvods.views.EventListActivity
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.binding.RecyclerViewAdapter
import com.filippovigani.eventvods.binding.RecyclerViewViewHolder
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.viewmodels.EventListViewModel
import com.filippovigani.eventvods.viewmodels.EventContentViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.event_list_content.*
import kotlinx.android.synthetic.main.event_list_content.view.*
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import java.lang.Exception
import android.graphics.drawable.ColorDrawable




class EventsAdapter(private val parentActivity: EventListActivity, items: List<Event>? = null) : RecyclerViewAdapter<Event>(items) {

	private val onClickListener: View.OnClickListener

	init {
		onClickListener = View.OnClickListener { view ->
			val event = view.tag as Event
			ViewModelProviders.of(parentActivity).get(EventListViewModel::class.java).selected = event
			val intent = Intent(view.context, EventDetailActivity::class.java).apply {
				putExtra(EventDetailActivity.ARG_EVENT_SLUG, event.slug)
				putExtra(EventDetailActivity.ARG_EVENT_NAME, event.name)
				putExtra(EventDetailActivity.ARG_EVENT_LOGO_URL, event.logo)
				putExtra(EventDetailActivity.ARG_EVENT_LOGO_BACKGROUND, (view.event_image.background as ColorDrawable?)?.color)
			}
			//TODO: make the whole card view pop up
			val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(parentActivity, view.event_image, ViewCompat.getTransitionName(parentActivity.event_image))
			view.context.startActivity(intent, options.toBundle())
		}
	}

	override fun getLayoutIdForPosition(position: Int) = R.layout.event_list_content

	override fun getViewModel(position: Int) = items?.let { EventContentViewModel(it[position]) }

	override fun getItemCount() = items?.size ?: 0

	override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
		val eventViewModel = getViewModel(position)?.apply {
			holder.bind(this)
		}

		holder.itemView.apply {
			tag = eventViewModel?.event
			setOnClickListener(onClickListener)
		}
	}

}