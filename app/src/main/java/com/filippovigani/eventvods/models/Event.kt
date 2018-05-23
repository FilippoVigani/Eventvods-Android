package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Event(@SerializedName("_id") val id: String?) {
	val name: String = "Event"
	//val game: Game
	val slug: String = ""
	val game: Game? = null
	val subtitle: String? = ""
	val startDate: Date? = null
	val endDate: Date? = null
	val logo: String? = null
	val status: String? = null
	val updatedAt: Date? = null
}