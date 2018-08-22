package com.filippovigani.eventvods.views

//import com.google.android.youtube.player.YouTubeInitializationResult
//import com.google.android.youtube.player.YouTubePlayer
//import com.google.android.youtube.player.YouTubePlayerSupportFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityMatchDetailBinding
import com.filippovigani.eventvods.viewmodels.MatchDetailViewModel
import com.filippovigani.eventvods.views.adapters.MatchGamePagerAdapter
import com.filippovigani.eventvods.views.utils.FullScreenHelper
import com.filippovigani.eventvods.views.utils.ThemeUtils
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.activity_match_detail.view.*
import kotlinx.android.synthetic.main.match_game.*
import kotlinx.android.synthetic.main.player_controls.*

@BindingMethods(
		BindingMethod(
				type = ImageView::class,
				attribute = "app:srcCompat",
				method = "setImageDrawable"
		)
)
class MatchDetailActivity : AppCompatActivity(), View.OnClickListener {
	private val fullScreenHelper = FullScreenHelper(this)

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

		binding.viewModel?.match?.observe(this, Observer { match ->
			sectionsPagerAdapter.games = match?.games
			if (binding.viewModel?.currentVODUrl == null){
				binding.viewModel?.currentVODUrl = match?.games?.get(0)?.youtube?.draft
			}
			if (match?.games != null && match.games.size <= 5) {
				binding.root.gamesTabLayout.tabMode = TabLayout.MODE_FIXED
			}
		})

		setSupportActionBar(detail_toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		initYouTubePlayerView()
		initPlayerControls()
	}

	private fun initPlayerControls() {
		listOf(togglePlaybackButton, skipForward1, skipForward2, skipForward3, skipBack1, skipBack2, skipBack3).forEach {it.setOnClickListener(this)}
	}

	override fun onClick(v: View?) {
		val vm =  ViewModelProviders.of(this@MatchDetailActivity).get(MatchDetailViewModel::class.java)
		when(v){
			togglePlaybackButton -> vm.togglePlayback()
			skipBack1 -> vm.skip(-5)
			skipBack2 -> vm.skip(-60)
			skipBack3 -> vm.skip(-300)
			skipForward1 -> vm.skip(5)
			skipForward2 -> vm.skip(60)
			skipForward3 -> vm.skip(300)
			gameStart, gameDraft -> vm.currentVODUrl = v?.tag as? String
		}
	}

	private fun initYouTubePlayerView() {
		lifecycle.addObserver(youtubePlayerView)

		youtubePlayerView.playerUIController.apply {
			showSeekBar(false)
			showCurrentTime(false)
			showBufferingProgress(false)
			showYouTubeButton(false)
			showDuration(false)
			showFullscreenButton(true)
		}

		youtubePlayerView.initialize({ ViewModelProviders.of(this@MatchDetailActivity).get(MatchDetailViewModel::class.java).player = it }, true)

		youtubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener {
			override fun onYouTubePlayerEnterFullScreen() {
				fullScreenHelper.enterFullScreen()
				requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
			}

			override fun onYouTubePlayerExitFullScreen() {
				fullScreenHelper.exitFullScreen()
				requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
			}
		})
	}

	override fun onConfigurationChanged(newConfiguration: Configuration) {
		super.onConfigurationChanged(newConfiguration)
		youtubePlayerView.playerUIController.menu?.dismiss()
	}

	override fun onBackPressed() {
		if (youtubePlayerView.isFullScreen)
			youtubePlayerView.exitFullScreen()
		else
			super.onBackPressed()
	}

	override fun onOptionsItemSelected(item: MenuItem) =
		when (item.itemId) {
			android.R.id.home -> {
				finishAfterTransition()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}

	public override fun onPause() {
		super.onPause()
	}

	public override fun onStop() {
		super.onStop()
	}

	public override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
		super.onRestoreInstanceState(savedInstanceState)
	}

	companion object {
		const val ARG_MATCH_ID = "match_id"
		const val ARG_GAME_SLUG = "game_slug"
	}
}
