package com.filippovigani.eventvods.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

object RecyclerViewBindingAdapters {
	@Suppress("UNCHECKED_CAST")
	@JvmStatic @BindingAdapter("items")
	fun <T> setItems(recyclerView: RecyclerView, items: List<T>?) {
		val adapter = recyclerView.adapter as RecyclerViewAdapter<T>?
		if (adapter != null) {
			adapter.items = items
		} else {
			throw IllegalStateException("Adapter not set")
		}
	}

	@JvmStatic @BindingAdapter("fixedSize")
	fun setFixedSize(recyclerVew: RecyclerView, fixedSize: Boolean) {
		recyclerVew.setHasFixedSize(fixedSize)
	}
}