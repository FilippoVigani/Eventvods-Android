package com.filippovigani.eventvods

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.networking.EventvodsApi
import com.filippovigani.eventvods.views.EventsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener{

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: EventsAdapter//RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager
	private lateinit var swipeRefreshLayout : SwipeRefreshLayout

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val context = this

		viewManager = LinearLayoutManager(context)
		viewAdapter = EventsAdapter()
		recyclerView = events_recycler_view.apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			adapter = viewAdapter

		}

		swipeRefreshLayout = swipe_refresh_layout.apply {
			setOnRefreshListener(context)
			setColorSchemeResources(R.color.primary, R.color.accent)
		}

		setSupportActionBar(toolbar)

		onRefresh()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onRefresh() {
		swipeRefreshLayout.isRefreshing = true
		EventvodsApi.getEvents {
			events -> viewAdapter.events = events
			swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
		}
	}
}
