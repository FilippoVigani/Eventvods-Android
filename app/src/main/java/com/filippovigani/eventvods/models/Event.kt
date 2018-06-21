package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(
		@SerializedName("_id") val id: String,
		val slug: String
) {
	val name: String = "Event"
	val game: Game? = null
	val subtitle: String? = ""
	val startDate: Date? = null
	val endDate: Date? = null
	val logo: String? = null
	val status: String? = null
	val updatedAt: Date? = null
	//@SerializedName("contents")
	val sections: List<Section>?
		get(){
			return listOf(Section("test"), Section("test2"), Section("test2"), Section("test2"), Section("test2"), Section("test2"))
		}

	data class Section(@SerializedName("_id") val id: String ) {
		var title: String = "Matches"
	}
}