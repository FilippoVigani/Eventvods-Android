package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.EventSectionBinding
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.models.Match
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.viewmodels.EventSectionViewModel
import com.filippovigani.eventvods.views.adapters.EventsAdapter
import com.filippovigani.eventvods.views.adapters.MatchesAdapter
import kotlinx.android.synthetic.main.event_list.*
import kotlinx.android.synthetic.main.match_list.*
import kotlinx.android.synthetic.main.match_list.view.*

class EventSectionFragment : Fragment() {
	private var sectionIndex: Int = 0
	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: MatchesAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sectionIndex = arguments?.getInt(ARG_SECTION_INDEX) ?: 0
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		val binding: EventSectionBinding = DataBindingUtil.inflate(inflater, R.layout.event_section, container, false)
		binding.setLifecycleOwner(this)
		activity?.let {
			viewAdapter = MatchesAdapter(null, it)
			val parentVM = ViewModelProviders.of(it).get(EventDetailViewModel::class.java)

			parentVM.event.observe(this, Observer { event ->
				val section = event?.sections?.get(sectionIndex)
				binding.viewModel = section
				val matches = ObservableArrayList<Match>()
				section?.modules?.forEach { module -> module.matches?.apply { matches.addAll(this) } }
				viewAdapter.items = matches
			})
		}


		recyclerView = binding.root.match_list.apply {
			setHasFixedSize(true)
			adapter = viewAdapter
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