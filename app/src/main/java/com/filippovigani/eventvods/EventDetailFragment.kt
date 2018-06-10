package com.filippovigani.eventvods

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filippovigani.eventvods.databinding.EventDetailBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import kotlinx.android.synthetic.main.activity_event_detail.*
import android.arch.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import android.view.ViewTreeObserver
import com.squareup.picasso.Callback


/**
 * A fragment representing a single Event detail screen.
 * This fragment is either contained in a [EventListActivity]
 * in two-pane mode (on tablets) or a [EventDetailActivity]
 * on handsets.
 */
class EventDetailFragment : Fragment() {

	var viewModel : EventDetailViewModel? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.getString(ARG_EVENT_ID)?.let {eventId ->
			viewModel = ViewModelProviders.of(this, EventDetailViewModel.Factory(eventId)).get(EventDetailViewModel::class.java)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		val binding: EventDetailBinding = DataBindingUtil.inflate(inflater, R.layout.event_detail, container, false)
		binding.setLifecycleOwner(this)
		binding.viewModel = viewModel

		return binding.root
	}

	companion object {
		const val ARG_EVENT_ID = "event_id"
		const val ARG_EVENT_NAME = "event_name"
		const val ARG_EVENT_LOGO_URL = "event_logo_url"
	}
}
