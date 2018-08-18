package com.filippovigani.eventvods.views.viewholders

import android.content.Intent
import android.databinding.ViewDataBinding
import android.view.View
import com.filippovigani.eventvods.R.id.reveal_btn
import com.filippovigani.eventvods.binding.RecyclerViewViewHolder
import com.filippovigani.eventvods.viewmodels.MatchContentViewModel
import com.filippovigani.eventvods.views.MatchDetailActivity
import kotlinx.android.synthetic.main.match_list_content.view.*

class MatchViewHolder(binding: ViewDataBinding) : RecyclerViewViewHolder(binding), View.OnClickListener {

	init {
		itemView.reveal_btn?.setOnClickListener(this)
		itemView.setOnClickListener(this)
	}

	override fun onClick(v: View) {
		val matchContent = (itemView.tag as? MatchContentViewModel) ?: return
		when (v.id){
			reveal_btn -> matchContent.reveal()
			itemView.id -> {
				if (matchContent.spoilerable && !matchContent.revealed.get()) return matchContent.reveal()
				val intent = Intent(itemView.context, MatchDetailActivity::class.java).apply {
					putExtra(MatchDetailActivity.ARG_MATCH_ID, matchContent.match.id)
					putExtra(MatchDetailActivity.ARG_GAME_SLUG, matchContent.event?.game?.slug)
				}
				itemView.context.startActivity(intent)
			}
		}
	}
}