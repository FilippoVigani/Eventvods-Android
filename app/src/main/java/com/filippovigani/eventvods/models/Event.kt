package com.filippovigani.eventvods.models

import java.util.*

data class Event(val id: String?) {
	val name: String = ""
	//val game: Game
	val slug: String = ""
	val subtitle: String? = ""
	val startDate: Date? = null
	val endDate: Date? = null
	val logo: String? = null
	val status: String? = null
	val updatedAt: Date? = null
}