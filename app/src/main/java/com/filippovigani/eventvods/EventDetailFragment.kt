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
		arguments?.let {
			if (it.containsKey(ARG_EVENT_ID)) {
				viewModel = ViewModelProviders.of(this, EventDetailViewModel.Factory(it[ARG_EVENT_ID] as String)).get(EventDetailViewModel::class.java)
			}
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		val binding: EventDetailBinding = DataBindingUtil.inflate(inflater, R.layout.event_detail, container, false)
		binding.setLifecycleOwner(this)
		binding.viewModel = viewModel?.also {
			it.event.observe(this, Observer{event -> activity?.toolbar_layout?.title = event?.name})
		}
		return binding.root
	}

	companion object {
		/**
		 * The fragment argument representing the item ID that this fragment
		 * represents.
		 */
		const val ARG_EVENT_ID = "event_id"
	}
}
