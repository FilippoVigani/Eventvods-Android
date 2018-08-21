package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityEventDetailBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.views.adapters.EventSectionsPagerAdapter
import com.filippovigani.eventvods.views.utils.ThemeUtils
import com.filippovigani.eventvods.views.utils.getAttribute
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
		super.onCreate(savedInstanceState)
		val gameSlug = intent.getStringExtra(ARG_GAME_SLUG)
		val eventSlug = intent.getStringExtra(ARG_EVENT_SLUG)
		val bgColor = intent.getIntExtra(ARG_EVENT_LOGO_BACKGROUND, R.color.cardview_dark_background)
		val eventLogoUrl = intent.getStringExtra(ARG_EVENT_LOGO_URL)

		setTheme(ThemeUtils.getGameTheme(gameSlug))

		val binding: ActivityEventDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
		binding.setLifecycleOwner(this)

		binding.viewModel = ViewModelProviders.of(this, EventDetailViewModel.Factory(eventSlug)).get(EventDetailViewModel::class.java)


		val sectionsPagerAdapter = EventSectionsPagerAdapter(supportFragmentManager)
		binding.root.sectionsViewPager.adapter = sectionsPagerAdapter

		binding.viewModel?.event?.observe(this, Observer{event ->
			sectionsPagerAdapter.sections = event?.sections
			if (event?.sections != null && event.sections.size <= 4){
				binding.root.sectionsTabLayout.tabMode = TabLayout.MODE_FIXED
			}
		})

		setSupportActionBar(detail_toolbar)

		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		event_image.setBackgroundColor(bgColor)
		Picasso.get().load(eventLogoUrl).into(event_image)

		swipe_refresh_layout.setOnRefreshListener {binding.viewModel?.loadEvent(forceFetch = true)}
		swipe_refresh_layout.setColorSchemeResources(theme.getAttribute(R.attr.colorPrimary), theme.getAttribute(R.attr.colorAccent))

		//This fixes an issue which caused horizontal swipe to not work properly when swiping down a little
		sectionsViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
			override fun onPageScrollStateChanged(state: Int) {
				swipe_refresh_layout?.isEnabled = state == ViewPager.SCROLL_STATE_IDLE
			}

			override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
			}

			override fun onPageSelected(position: Int) {
			}
		})
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.menu_main, menu)
		return true
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
