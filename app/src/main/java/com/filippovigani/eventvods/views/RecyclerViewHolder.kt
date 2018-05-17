package com.filippovigani.eventvods.views

import android.support.v7.widget.RecyclerView
import android.databinding.ViewDataBinding
import com.android.databinding.library.baseAdapters.BR


open class RecyclerViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

	fun bind(viewModel: Any) : Boolean{
		val bindingSuccess = binding.setVariable(BR.viewModel, viewModel)
		if (bindingSuccess) binding.executePendingBindings()
		return bindingSuccess
	}
}