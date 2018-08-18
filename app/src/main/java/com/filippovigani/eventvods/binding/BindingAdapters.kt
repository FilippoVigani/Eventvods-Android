package com.filippovigani.eventvods.binding

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.graphics.Palette
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.filippovigani.eventvods.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


object BindingAdapters{

	@JvmStatic @BindingAdapter("gameSlug")
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

	@JvmStatic @BindingAdapter("src")
	fun setImageViewResource(imageView: ImageView, src: String?) {
		if (src == null){
			imageView.setImageDrawable(null)
			return
		}
		Picasso.get().load(src).into(imageView)
	}

	val palettes: HashMap<String, Palette> = HashMap()

	@JvmStatic @BindingAdapter("eventLogo")
	fun setEventLogo(imageView: ImageView, src: String?) {
		if (src == null){
			imageView.setImageDrawable(null)
			return
		}
		val imageViewTarget = object : Target {
			override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
				bitmap?.let {
					val paletteCached = palettes[src]
					if (paletteCached != null){
						val backgroundColor = paletteCached.getMutedColor(ContextCompat.getColor(imageView.context, R.color.cardview_dark_background))
						imageView.setBackgroundColor(backgroundColor)
						imageView.setImageBitmap(it)
					} else {
						Palette.from(it).generate { palette ->
							palettes[src] = palette
							val backgroundColor = palette.getMutedColor(ContextCompat.getColor(imageView.context, R.color.cardview_dark_background))
							imageView.setBackgroundColor(backgroundColor)
							imageView.setImageBitmap(it)
							imageView.startAnimation(AnimationUtils.loadAnimation(imageView.context, R.anim.fadein))
						}
					}
				}
			}

			override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
				imageView.setImageDrawable(placeHolderDrawable)
				(imageView.tag as Target?)?.let {
					imageView.setBackgroundColor(ContextCompat.getColor(imageView.context, R.color.cardview_light_background))
					Picasso.get().cancelRequest(it)
				}
				imageView.tag = this
			}

			override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
				imageView.setImageDrawable(errorDrawable)
			}
		}

		Picasso.get().load(src).into(imageViewTarget)

	}

	@JvmStatic @BindingAdapter("isRefreshing")
	fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean?){
		swipeRefreshLayout.isRefreshing = isRefreshing == true
	}
}

