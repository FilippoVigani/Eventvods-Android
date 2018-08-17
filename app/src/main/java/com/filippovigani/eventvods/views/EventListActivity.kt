package com.filippovigani.eventvods.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityEventListBinding
import com.filippovigani.eventvods.viewmodels.EventListViewModel
import com.filippovigani.eventvods.views.adapters.EventsAdapter
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.event_list.*

class EventListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val binding: ActivityEventListBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)
		binding.setLifecycleOwner(this)
		binding.viewModel = ViewModelProviders.of(this).get(EventListViewModel::class.java)

		toolbar.title = title
		setSupportActionBar(toolbar)

		event_list.adapter = EventsAdapter(this)

		swipe_refresh_layout.setOnRefreshListener(this)
		swipe_refresh_layout.setColorSchemeResources(theme.getAttribute(R.attr.colorPrimary), theme.getAttribute(R.attr.colorAccent))
	}

	override fun onRefresh() {
		ViewModelProviders.of(this).get(EventListViewModel::class.java).loadEvents()
	}
}
