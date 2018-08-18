package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName
import android.net.UrlQuerySanitizer



data class Match(@SerializedName("_id") val id: String ) {
	@SerializedName("team1Match")
	val team1Placeholder: String? = null
	@SerializedName("team2Match")
	val team2Placeholder: String? = null

	val spoiler1: Boolean = false
	val spoiler2: Boolean = false
	val bestOf: Int = 1

	val team1: Team? = null
	val team2: Team? = null

	@SerializedName("data")
	val games: List<Game>? = null

	data class Game(@SerializedName("_id") val id: String ){
		val youtube: VOD? = null
		val twitch: VOD? = null

		val links: List<String>? = null

		class VOD{
			val gameStart: String? = null
			val picksBans: String? = null


			val id: String?
				get() = UrlQuerySanitizer(gameStart).getValue("v")
		}
	}
}