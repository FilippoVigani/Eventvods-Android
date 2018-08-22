package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableBoolean
import android.util.Log
import com.filippovigani.eventvods.models.Match
import com.filippovigani.eventvods.services.EventvodsRepository
import com.pierfrancescosoffritti.androidyoutubeplayer.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.utils.YouTubePlayerTracker

class MatchDetailViewModel(matchId : String) : ViewModel(){

	val match: LiveData<Match> = EventvodsRepository.getMatch(matchId)

	var currentVODUrl : String? = null
		set(value){
			field = value
			player?.loadCurrentVOD()
		}
	val isPlaying: ObservableBoolean = ObservableBoolean(false)

	var player : YouTubePlayer? = null
		set(value){
			if (field != value){
				field = value
				field?.addListener(playerTracker)
			}
		}

	private val playerTracker = object : YouTubePlayerTracker(){

		override fun onReady() {
			super.onReady()
			player?.loadCurrentVOD()
		}

		override fun onStateChange(state: PlayerConstants.PlayerState) {
			super.onStateChange(state)
			when(state){
				PlayerConstants.PlayerState.PLAYING -> isPlaying.set(true)
				PlayerConstants.PlayerState.PAUSED -> isPlaying.set(false)
				PlayerConstants.PlayerState.ENDED -> isPlaying.set(false)
				else -> { }
			}
		}

		override fun onError(error: PlayerConstants.PlayerError) {
			super.onError(error)
			Log.e("YoutubePlayer", error.toString())
		}
	}

	fun YouTubePlayer.loadCurrentVOD(){
		val id = Match.Game.VOD.id(currentVODUrl)
		val start = Match.Game.VOD.startSeconds(currentVODUrl)
		if (id == playerTracker.videoId){
			this.seekTo(start.toFloat())
		} else {
			this.loadVideo(id ?: "", start.toFloat())
		}
	}
	fun skip(seconds: Int) = player?.skip(seconds)
	fun togglePlayback() = player?.togglePlayback()

	fun YouTubePlayer.skip(seconds: Int){
		this.seekTo(playerTracker.currentSecond + seconds)
	}

	fun YouTubePlayer.togglePlayback(){
		if (isPlaying.get()) player?.pause()
		else this.play()
	}

	class Factory(private val matchId: String) : ViewModelProvider.NewInstanceFactory() {

		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return MatchDetailViewModel(matchId) as T
		}
	}
}