package com.filippovigani.eventvods.networking

import android.os.AsyncTask
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.setFollowRedirects



class HttpsRequestTask(private val callback: (String) -> Unit) : AsyncTask<URL, Unit, Unit>() {
	override fun doInBackground(vararg params: URL){
		for (url in params){
			try {
				val urlConnection: URLConnection = url.openConnection()
				val inputStream: InputStream = urlConnection.getInputStream()
				val response = inputStream.bufferedReader().use { it.readText() }
				callback(response)
			} catch (e: java.net.SocketTimeoutException) {
			} catch (e: java.io.IOException) {
			}
		}
	}
}