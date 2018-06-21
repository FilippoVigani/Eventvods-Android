package com.filippovigani.eventvods.models

import com.google.gson.annotations.SerializedName

class Game(@SerializedName("_id") val id: String) {
	val slug: String = ""
	val name: String? = null
}