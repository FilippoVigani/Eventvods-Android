package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName

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
}