package com.filippovigani.eventvods.views.adapters

import android.app.Activity
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.binding.*
import com.filippovigani.eventvods.models.*

class MatchesAdapter(items: Collection<Match>? = null,
                     private val parentActivity: Activity) : RecyclerViewAdapter<Match>(items){

	override fun getViewModel(position: Int) = items?.get(position)

	override fun getItemCount() = items?.size ?: 0

	override fun getLayoutIdForPosition(position: Int)= R.layout.match_list_content

	override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
		val matchViewModel = getViewModel(position)?.apply {
			holder.bind(this)
		}

		holder.itemView.apply {
			tag = matchViewModel
		}
	}

}