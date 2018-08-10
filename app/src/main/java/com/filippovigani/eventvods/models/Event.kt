package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(
		@SerializedName("_id") val id: String,
		val slug: String
) {
	var complete = false

	val name: String = "Event"
	val game: Game? = null
	val subtitle: String? = ""
	val startDate: Date? = null
	val endDate: Date? = null
	val logo: String? = null
	val status: String? = null
	val updatedAt: Date? = null

	@SerializedName("contents")
	val sections: List<Section>? = null

	data class Section(@SerializedName("_id") val id: String ) {
		var title: String = "Matches"
		var columns: List<String>? = null
		var date: Date? = null
		var twitch: Boolean = false
		var youtube: Boolean = false
		var facebook: Boolean = false
		var modules: List<Module>? = null


		data class Module(@SerializedName("_id") val id: String ) {
			var title: String = "Module"
			@SerializedName("matches2")
			var matches: List<Match>? = null
		}
	}
}