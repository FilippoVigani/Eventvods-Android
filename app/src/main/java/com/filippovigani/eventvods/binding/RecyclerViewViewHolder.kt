package com.filippovigani.eventvods.binding

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import com.android.databinding.library.baseAdapters.BR


open class RecyclerViewViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

	fun bind(viewModel: Any){
		val bindingSuccess = binding.setVariable(BR.viewModel, viewModel)
		if (!bindingSuccess) {
			throw IllegalStateException("Binding ${this.binding} viewModel variable name should be 'viewModel'")
		}
		binding.executePendingBindings()
	}
}