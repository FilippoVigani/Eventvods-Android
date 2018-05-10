package com.filippovigani.eventvods.networking

import java.net.URL

enum class Endpoint(service: String) {
	EVENTS("/events"),
	GAME("/events/slug");

	private val baseUrl = "https://www.eventvods.com/api"

	private val path: String = baseUrl + service

	val url: URL = URL(this.path)
}