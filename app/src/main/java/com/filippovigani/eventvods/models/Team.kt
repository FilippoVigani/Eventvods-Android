package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName

class Team(@SerializedName("identifier") val id: String ) {
	val name: String? = null
	val tag: String? = null
	val slug: String? = null
	val icon: String? = null
}