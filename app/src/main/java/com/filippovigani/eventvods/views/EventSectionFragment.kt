package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.EventSectionBinding
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.viewmodels.EventSectionViewModel

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
			val parentVM = ViewModelProviders.of(it).get(EventDetailViewModel::class.java)

			parentVM.event.observe(this, Observer { event ->
				binding.viewModel = event?.sections?.get(sectionIndex)
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