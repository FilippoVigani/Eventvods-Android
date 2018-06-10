package com.filippovigani.eventvods

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.filippovigani.eventvods.databinding.ActivityEventListBinding

import com.filippovigani.eventvods.viewmodels.EventListViewModel
import com.filippovigani.eventvods.views.EventsAdapter
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.event_list.*


/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [EventDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class EventListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: EventsAdapter//RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager
	private lateinit var swipeRefreshLayout : SwipeRefreshLayout
	private var twoPane: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val binding: ActivityEventListBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_list)
		val viewModel = ViewModelProviders.of(this).get(EventListViewModel::class.java)
		binding.viewModel = viewModel

		setSupportActionBar(toolbar)
		toolbar.title = title

		val context = this

		viewManager = LinearLayoutManager(context)
		viewAdapter = EventsAdapter(null, this, twoPane)
		recyclerView = event_list.apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			adapter = viewAdapter
		}

		viewModel.events.observe(this, Observer {events ->
			viewAdapter.items = events
		})

		viewModel.loading.observe(this, Observer { loading ->
			swipeRefreshLayout.isRefreshing = loading ?: false
		})

		swipeRefreshLayout = swipe_refresh_layout.apply {
			setOnRefreshListener(context)
			setColorSchemeResources(R.color.primary, R.color.accent)
		}

		setSupportActionBar(toolbar)

		/*fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}*/

		if (event_detail_container != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-w900dp).
			// If this view is present, then the
			// activity should be in two-pane mode.
			twoPane = true
		}
	}

	override fun onRefresh() {
		ViewModelProviders.of(this).get(EventListViewModel::class.java).loadEvents()
	}
}
