package com.filippovigani.eventvods

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.support.design.widget.Snackbar
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

class MainActivity : AppCompatActivity() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: EventsAdapter//RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		viewManager = LinearLayoutManager(this)
		//val events = ArrayList<Event>(Arrays.asList(Event("Test event 1"), Event("Test event 2")))
		viewAdapter = EventsAdapter()

		recyclerView = events_recycler_view.apply {
			setHasFixedSize(true)
			layoutManager = viewManager
			adapter = viewAdapter

		}

		EventvodsApi.getEvents {events -> viewAdapter.events = events}


		/*val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		binding.event = Event("Test event")*/

		setSupportActionBar(toolbar)
		/*fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}*/
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
}
