package com.filippovigani.eventvods.binding

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.PorterDuff
import android.widget.ImageView
import com.filippovigani.eventvods.R
import com.squareup.picasso.Picasso


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

@BindingAdapter("src")
fun setImageViewResource(imageView: ImageView, src: String) {
	Picasso.get().load(src).into(imageView)
}