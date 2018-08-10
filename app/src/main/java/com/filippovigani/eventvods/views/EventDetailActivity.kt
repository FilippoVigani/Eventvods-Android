package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityEventDetailBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.views.adapters.EventSectionsPagerAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_event_detail.view.*

/**
 * An activity representing a single Event detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [EventListActivity].
 */
class EventDetailActivity : AppCompatActivity() {

	private lateinit var sectionsPagerAdapter: EventSectionsPagerAdapter
	private lateinit var viewModel : EventDetailViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val binding: ActivityEventDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
		intent.getStringExtra(ARG_EVENT_SLUG)?.let { eventSlug ->
			viewModel = ViewModelProviders.of(this, EventDetailViewModel.Factory(eventSlug)).get(EventDetailViewModel::class.java)
		}
		binding.viewModel = viewModel

		sectionsPagerAdapter = EventSectionsPagerAdapter(supportFragmentManager)
		binding.root.sectionsViewPager.adapter = sectionsPagerAdapter

		viewModel.event.observe(this, Observer{event ->
			sectionsPagerAdapter.sections = event?.sections
			event?.let {
				toolbar_layout.title = it.name
			}
		})

		setSupportActionBar(detail_toolbar)

		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)


		event_image.setBackgroundColor(intent.extras.getInt(ARG_EVENT_LOGO_BACKGROUND))
		Picasso.get().load(intent.extras.getString(ARG_EVENT_LOGO_URL)).into(event_image)

	}

	override fun onOptionsItemSelected(item: MenuItem) =
		when (item.itemId) {
			android.R.id.home -> {
				// This ID represents the Home or Up button. In the case of this
				// activity, the Up button is shown. For
				// more details, see the Navigation pattern on Android Design:
				//
				// http://developer.android.com/design/patterns/navigation.html#up-vs-back
				finishAfterTransition()
				//navigateUpTo(Intent(this, EventListActivity::class.java))
				true
			}
			else -> super.onOptionsItemSelected(item)
		}

	companion object {
		const val ARG_EVENT_SLUG = "event_slug"
		const val ARG_EVENT_NAME = "event_name"
		const val ARG_EVENT_LOGO_URL = "event_logo_url"
		const val ARG_EVENT_LOGO_BACKGROUND = "event_logo_background"
	}
}
