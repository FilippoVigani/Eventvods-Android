package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.EventSectionBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.viewmodels.EventSectionViewModel
import com.filippovigani.eventvods.views.adapters.MatchesAdapter
import io.doist.recyclerviewext.sticky_headers.StickyHeadersLinearLayoutManager
import kotlinx.android.synthetic.main.match_list.view.*

class EventSectionFragment : Fragment() {
	private var sectionIndex: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sectionIndex = arguments?.getInt(ARG_SECTION_INDEX) ?: 0
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		val binding: EventSectionBinding = DataBindingUtil.inflate(inflater, R.layout.event_section, container, false)
		binding.setLifecycleOwner(this)
		activity?.let {
			binding.root.match_list.layoutManager = StickyHeadersLinearLayoutManager<MatchesAdapter>(context)
			binding.root.match_list.adapter = MatchesAdapter(it)

			val parentVM = ViewModelProviders.of(it).get(EventDetailViewModel::class.java)

			parentVM.event.observe(this, Observer { event ->
				(binding.root.match_list.adapter as? MatchesAdapter)?.event = event
				event?.sections?.get(sectionIndex)?.let {
					binding.viewModel = ViewModelProviders.of(this, EventSectionViewModel.Factory(it)).get(EventSectionViewModel::class.java)
				}
			})

		}

		return binding.root
	}

	companion object {
		const val ARG_SECTION_INDEX = "section_index"

		fun newInstance(index: Int): EventSectionFragment {
			return EventSectionFragment().apply {
				arguments = Bundle().apply {
					putInt(ARG_SECTION_INDEX, index)
				}
			}
		}
	}
}