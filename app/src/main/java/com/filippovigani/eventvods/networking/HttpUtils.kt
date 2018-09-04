package com.filippovigani.eventvods.networking

import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class HttpUtils {

	companion object {
		fun resolveUrl(shortUrl: String?) : String?{
			var unresolvedUrl = shortUrl
			var url : String? = null
			while (unresolvedUrl != null && unresolvedUrl != url){
				url = unresolvedUrl
				val conn = try {URL(unresolvedUrl).openConnection() as HttpURLConnection } catch (e: Exception) {null}
				conn?.instanceFollowRedirects = false
				unresolvedUrl = conn?.getHeaderField("Location")
			}
			return url
		}
	}
}