package com.filippovigani.eventvods.networking

import java.net.URL

enum class Endpoint(service: String) {
	EVENTS("/events"),
	GAME("/events/slug"),
	EVENT("/events/slug"),;

	private val baseUrl = "https://www.eventvods.com/api"

	private val path: String = baseUrl + service

	fun url(vararg params: String): String {
		var url = this.path
		for (param in params) url += "/$param"
		return url
	}
	val url: URL = URL(this.path)
}