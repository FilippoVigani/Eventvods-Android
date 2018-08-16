package com.filippovigani.eventvods.views

import android.databinding.ViewDataBinding
import android.view.View
import com.filippovigani.eventvods.binding.RecyclerViewViewHolder
import com.filippovigani.eventvods.viewmodels.MatchViewModel
import kotlinx.android.synthetic.main.match_list_content.view.*

class MatchViewHolder(binding: ViewDataBinding) : RecyclerViewViewHolder(binding), View.OnClickListener {

	init {
		itemView.reveal_btn?.setOnClickListener { btn ->
			(itemView.tag as? MatchViewModel)?.reveal()
		}
	}
	override fun onClick(v: View?) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}