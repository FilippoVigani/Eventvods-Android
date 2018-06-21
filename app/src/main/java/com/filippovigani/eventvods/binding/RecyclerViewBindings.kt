package com.filippovigani.eventvods.binding

import android.databinding.BindingAdapter
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

object RecyclerViewBindings {
	private val KEY_ITEMS = -123
	private val KEY_CLICK_HANDLER = -124
	private val KEY_LONG_CLICK_HANDLER = -125

	@BindingAdapter("items")
	fun <T> setItems(recyclerView: RecyclerView, items: Collection<T>) {
		val adapter = recyclerView.adapter as RecyclerViewAdapter<T>?
		if (adapter != null) {
			adapter.items = items as ObservableList<T>
		} else {
			recyclerView.setTag(KEY_ITEMS, items)
		}
	}
}