package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.view.MenuItem
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityMatchDetailBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.viewmodels.MatchContentViewModel
import com.filippovigani.eventvods.viewmodels.MatchDetailViewModel
import com.filippovigani.eventvods.views.adapters.MatchGamePagerAdapter
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.activity_match_detail.view.*

class MatchDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val gameSlug = intent.getStringExtra(ARG_GAME_SLUG)
		val matchId = intent.getStringExtra(MatchDetailActivity.ARG_MATCH_ID)

		setTheme(ThemeUtils.getGameTheme(gameSlug))

		val binding: ActivityMatchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_match_detail)
		binding.setLifecycleOwner(this)
		matchId?.let { binding.viewModel = ViewModelProviders.of(this, MatchDetailViewModel.Factory(it)).get(MatchDetailViewModel::class.java) }

		val sectionsPagerAdapter = MatchGamePagerAdapter(supportFragmentManager)
		binding.root.gamesViewPager.adapter = sectionsPagerAdapter

		binding.viewModel?.match?.observe(this, Observer{match ->
			sectionsPagerAdapter.games = match?.games
			if (match?.games != null && match.games.size <= 5){
				binding.root.gamesTabLayout.tabMode = TabLayout.MODE_FIXED
			}
		})

		setSupportActionBar(detail_toolbar)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
		const val ARG_MATCH_ID = "match_id"
		const val ARG_GAME_SLUG = "game_slug"
	}
}
