package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.filippovigani.eventvods.BuildConfig
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityMatchDetailBinding
import com.filippovigani.eventvods.viewmodels.MatchDetailViewModel
import com.filippovigani.eventvods.views.adapters.MatchGamePagerAdapter
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.activity_match_detail.view.*

class MatchDetailActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

	private var player: YouTubePlayer? = null

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

		val playerFragment = youtubePlayerFragment as? YouTubePlayerSupportFragment ?: return

		playerFragment.initialize(BuildConfig.YoutubeAPIKey, this)

		binding.viewModel?.match?.observe(this, Observer { match ->
			sectionsPagerAdapter.games = match?.games
			binding?.viewModel?.currentVOD = match?.games?.get(0)?.youtube
			playCurrentVOD()
			if (match?.games != null && match.games.size <= 5) {
				binding.root.gamesTabLayout.tabMode = TabLayout.MODE_FIXED
			}
		})

		setSupportActionBar(detail_toolbar)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	private fun playCurrentVOD(){
		val vod = ViewModelProviders.of(this).get(MatchDetailViewModel::class.java).currentVOD
		player?.apply {
			loadVideo(vod?.id)
		}
	}

	override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
		if (!wasRestored) {
			this.player = player
			player?.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
		}
		playCurrentVOD()
	}

	override fun onInitializationFailure(provider: YouTubePlayer.Provider?, player: YouTubeInitializationResult?) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
