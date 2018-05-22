package com.filippovigani.eventvods.binding

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.databinding.ObservableList
import java.lang.ref.WeakReference

abstract class RecyclerViewAdapter<T>(items: Collection<T>? = null) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

	private val onListChangedCallback: WeakReferenceOnListChangedCallback<T>?

	protected var items: ObservableList<T>? = null
		set(value){
			if (field == value) return

			field?.let {
				notifyItemRangeRemoved(0, it.size)
				it.removeOnListChangedCallback(this.onListChangedCallback)
			}
			field = value
			notifyItemRangeInserted(0, field?.size ?: 0)
			field?.addOnListChangedCallback(this.onListChangedCallback)
		}

	init {
		this.onListChangedCallback = WeakReferenceOnListChangedCallback(this)

		items?.let{
			if (it is ObservableList<*>) {
				this.items = it as ObservableList<T>
			} else {
				ObservableArrayList<T>().let { list ->
					list.addAll(it)
					this.items = list
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
			RecyclerViewViewHolder(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false))

	override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
		getViewModel(position)?.let {holder.bind(it)}
	}

	override fun getItemViewType(position: Int) = getLayoutIdForPosition(position)

	abstract fun getLayoutIdForPosition(position: Int): Int

	abstract fun getViewModel(position: Int): Any?

	private class WeakReferenceOnListChangedCallback<T>(bindingRecyclerViewAdapter: RecyclerViewAdapter<T>) : ObservableList.OnListChangedCallback<ObservableList<T>>() {

		private val adapterReference: WeakReference<RecyclerViewAdapter<T>> = WeakReference(bindingRecyclerViewAdapter)

		override fun onChanged(sender: ObservableList<T>) {
			adapterReference.get()?.run { this.notifyDataSetChanged() }
		}

		override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
			adapterReference.get()?.run { this.notifyItemRangeChanged(positionStart, itemCount) }
		}

		override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
			adapterReference.get()?.run { this.notifyItemRangeInserted(positionStart, itemCount) }
		}

		override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
			adapterReference.get()?.run { this.notifyItemMoved(fromPosition, toPosition) }
		}

		override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
			adapterReference.get()?.run { this.notifyItemRangeRemoved(positionStart, itemCount) }
		}
	}
}