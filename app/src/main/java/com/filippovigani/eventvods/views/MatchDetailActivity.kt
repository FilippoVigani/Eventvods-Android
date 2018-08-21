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
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityMatchDetailBinding
import com.filippovigani.eventvods.viewmodels.MatchDetailViewModel
import com.filippovigani.eventvods.views.adapters.MatchGamePagerAdapter
import com.filippovigani.eventvods.views.utils.FullScreenHelper
import com.filippovigani.eventvods.views.utils.ThemeUtils
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerFullScreenListener
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.activity_match_detail.view.*

@BindingMethods(
		BindingMethod(
				type = ImageView::class,
				attribute = "app:srcCompat",
				method = "setImageDrawable"
		)
)
class MatchDetailActivity : AppCompatActivity(), View.OnClickListener {

	private var player: YouTubePlayer? = null
	private val fullScreenHelper = FullScreenHelper(this)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val gameSlug = intent.getStringExtra(ARG_GAME_SLUG)
		val matchId = intent.getStringExtra(MatchDetailActivity.ARG_MATCH_ID)

		setTheme(ThemeUtils.getGameTheme(gameSlug))

		val binding: ActivityMatchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_match_detail)
		//menuInflater.inflate(R.menu.menu_main, binding.root.player_menu.menu)
		binding.setLifecycleOwner(this)
		matchId?.let { binding.viewModel = ViewModelProviders.of(this, MatchDetailViewModel.Factory(it)).get(MatchDetailViewModel::class.java) }

		val sectionsPagerAdapter = MatchGamePagerAdapter(supportFragmentManager)
		binding.root.gamesViewPager.adapter = sectionsPagerAdapter

		//val playerFragment = youtubePlayerFragment as? YouTubePlayerSupportFragment ?: return
		//playerFragment.initialize(BuildConfig.YoutubeAPIKey, this)

		binding.viewModel?.match?.observe(this, Observer { match ->
			sectionsPagerAdapter.games = match?.games
			binding.viewModel?.currentVOD = match?.games?.get(0)?.youtube
			playCurrentVOD()
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
		when(v){
			togglePlaybackButton -> togglePlayback()
			skipBack1 -> skip(-5)
			skipBack2 -> skip(-60)
			skipBack3 -> skip(-300)
			skipForward1 -> skip(5)
			skipForward2 -> skip(60)
			skipForward3 -> skip(300)
		}
	}

	private fun skip(seconds: Int){
		val currentTime = ViewModelProviders.of(this).get(MatchDetailViewModel::class.java).playbackTime
		player?.seekTo(currentTime + seconds)
	}

	private fun togglePlayback(){
		val isPlaying = ViewModelProviders.of(this).get(MatchDetailViewModel::class.java).isPlaying.get()
		if (isPlaying) player?.pause()
		else player?.play()
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

		youtubePlayerView.initialize({ youTubePlayer ->

			this@MatchDetailActivity.player = youTubePlayer.apply {
				addListener(MatchDetailYoutubePlayerListener(this@MatchDetailActivity, this))
			}

		}, true)

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


	class MatchDetailYoutubePlayerListener(private val context: FragmentActivity, private val player: YouTubePlayer) : AbstractYouTubePlayerListener() {

		private val viewModel get() = ViewModelProviders.of(context).get(MatchDetailViewModel::class.java)

		override fun onReady() {
			viewModel.currentVOD?.id?.let {
				player.loadVideo(it, 0f)
			}
		}
		override fun onError(error: PlayerConstants.PlayerError) {
			Log.e("err", error.toString())
		}

		override fun onCurrentSecond(second: Float) {
			viewModel.playbackTime = second
		}

		override fun onStateChange(state: PlayerConstants.PlayerState) {
			when(state){
				PlayerConstants.PlayerState.PLAYING -> viewModel.isPlaying.set(true)
				PlayerConstants.PlayerState.PAUSED -> viewModel.isPlaying.set(false)
			}
		}
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


	private fun playCurrentVOD(){
		val vod = ViewModelProviders.of(this).get(MatchDetailViewModel::class.java).currentVOD
		vod?.id?.let {
			player?.loadVideo(it, 0f)
		}
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
		//player?.release()
	}

	public override fun onStop() {
		super.onStop()
		//player?.release()
	}

	public override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
		super.onRestoreInstanceState(savedInstanceState)
		player?.play()
	}

	companion object {
		const val ARG_MATCH_ID = "match_id"
		const val ARG_GAME_SLUG = "game_slug"
	}
}
