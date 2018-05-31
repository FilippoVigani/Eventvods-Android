package com.filippovigani.eventvods.binding

import android.arch.lifecycle.MutableLiveData
import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.widget.ImageView
import com.filippovigani.eventvods.R
import android.support.v7.widget.RecyclerView
import com.filippovigani.eventvods.EventListActivity
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.views.EventsAdapter



@BindingAdapter("gameSlug")
fun setGameLogo(view: ImageView, gameSlug: String) {
	val drawable = when(gameSlug){
		"dota" -> R.drawable.dota2_logo
		"lol" -> R.drawable.lol_logo
		"csgo" -> R.drawable.csgo_logo
		"rocket-league" -> R.drawable.rocketleague_logo
		"overwatch" -> R.drawable.overwatch_logo
		"pubg" -> R.drawable.pubg_logo
		else -> R.drawable.generic_game_logo
	}
	view.setImageResource(drawable)
}