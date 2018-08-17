package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.MenuItem
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityEventDetailBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.viewmodels.EventListViewModel
import com.filippovigani.eventvods.views.adapters.EventSectionsPagerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_event_detail.view.*

/**
 * An activity representing a single Event detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [EventListActivity].
 */
class EventDetailActivity : AppCompatActivity(){
	override fun onCreate(savedInstanceState: Bundle?) {
		setTheme(ThemeUtils.getGameTheme(intent.getStringExtra(ARG_GAME_SLUG)))
		super.onCreate(savedInstanceState)

		val binding: ActivityEventDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
		binding.setLifecycleOwner(this)
		intent.getStringExtra(ARG_EVENT_SLUG)?.let { eventSlug ->
			binding.viewModel = ViewModelProviders.of(this, EventDetailViewModel.Factory(eventSlug)).get(EventDetailViewModel::class.java)
		}

		val sectionsPagerAdapter = EventSectionsPagerAdapter(supportFragmentManager)
		binding.root.sectionsViewPager.adapter = sectionsPagerAdapter

		binding.viewModel?.event?.observe(this, Observer{event ->
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

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		event_image.setBackgroundColor(intent.getIntExtra(ARG_EVENT_LOGO_BACKGROUND, R.color.cardview_dark_background))
		Picasso.get().load(intent.getStringExtra(ARG_EVENT_LOGO_URL)).into(event_image)

		swipe_refresh_layout.setOnRefreshListener {binding.viewModel?.reloadEvent()}
		swipe_refresh_layout.setColorSchemeResources(theme.getAttribute(R.attr.colorPrimary), theme.getAttribute(R.attr.colorAccent))
	}

	override fun onOptionsItemSelected(item: MenuItem) =
		when (item.itemId) {
			android.R.id.home -> {
				finishAfterTransition()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}

	companion object {
		const val ARG_EVENT_SLUG = "event_slug"
		const val ARG_GAME_SLUG = "game_slug"
		const val ARG_EVENT_LOGO_URL = "event_logo_url"
		const val ARG_EVENT_LOGO_BACKGROUND = "event_logo_background"
	}
}
