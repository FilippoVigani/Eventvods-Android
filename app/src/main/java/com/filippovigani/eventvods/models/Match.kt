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
			@SerializedName("picksBans")
			val draft: String? = null


			companion object {
				fun id(url: String?): String? = url?.let { UrlQuerySanitizer(it).getValue("v") }

				fun startSeconds(url: String?) : Int {
					val time = url?.let { UrlQuerySanitizer(it).getValue("t") } ?: return 0
					val matches = Regex("(?:([0-9]+)(?:m))?(?:([0-9]+)(?:s))?").matchEntire(time)

					return (matches?.groups?.get(1)?.value?.toIntOrNull() ?: 0) * 60 + (matches?.groups?.get(2)?.value?.toIntOrNull() ?: 0)
				}
			}
		}
	}
}