package com.filippovigani.eventvods

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.views.EventsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var viewAdapter: RecyclerView.Adapter<*>
	private lateinit var viewManager: RecyclerView.LayoutManager


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		//EventvodsApi.getEvents()

		viewManager = LinearLayoutManager(this)
		val events : List<Event> = ArrayList<Event>(Arrays.asList(Event("Test event 1"), Event("Test event 2")))
		viewAdapter = EventsAdapter(events)

		recyclerView = events_recycler_view.apply {
			// use this setting to improve performance if you know that changes
			// in content do not change the layout size of the RecyclerView
			setHasFixedSize(true)

			// use a linear layout manager
			layoutManager = viewManager

			// specify an viewAdapter (see also next example)
			adapter = viewAdapter

		}


		/*val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
		binding.event = Event("Test event")*/

		setSupportActionBar(toolbar)
		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}
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
