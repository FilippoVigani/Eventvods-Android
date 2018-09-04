package com.filippovigani.eventvods.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.MatchGameBinding
import com.filippovigani.eventvods.viewmodels.MatchDetailViewModel
import com.filippovigani.eventvods.viewmodels.MatchGameViewModel
import kotlinx.android.synthetic.main.match_game.*
import kotlinx.android.synthetic.main.match_game.view.*

class MatchGameFragment : Fragment(), View.OnClickListener {
	private var gameIndex: Int = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		gameIndex = arguments?.getInt(ARG_GAME_INDEX) ?: 0
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		val binding: MatchGameBinding = DataBindingUtil.inflate(inflater, R.layout.match_game, container, false)
		binding.setLifecycleOwner(this)
		activity?.let {
			val parentVM = ViewModelProviders.of(it).get(MatchDetailViewModel::class.java)

			parentVM.match.observe(this, Observer { match ->
				match?.games?.get(gameIndex)?.let{
					binding.viewModel = ViewModelProviders.of(this, MatchGameViewModel.Factory(match, it)).get(MatchGameViewModel::class.java)
				}
			})
		}

		initPlayerControls(binding.root)
		return binding.root
	}

	private fun initPlayerControls(root: View?) {
		listOf(root?.gameStart, root?.gameDraft, root?.gameHighlights).forEach {it?.setOnClickListener(this)}
	}

	override fun onClick(v: View?) {
		activity?.let {
			val vm =  ViewModelProviders.of(it).get(MatchDetailViewModel::class.java)
			val gameVM = ViewModelProviders.of(this).get(MatchGameViewModel::class.java)
			when(v){
				gameStart -> vm.currentVODUrl = gameVM.game.youtube?.gameStart
				gameDraft -> vm.currentVODUrl = gameVM.game.youtube?.draft
				gameHighlights -> vm.currentVODUrl = gameVM.highlightsUrl
			}
		}
	}

	companion object {
		const val ARG_GAME_INDEX = "game_index"

		fun newInstance(index: Int): MatchGameFragment {
			return MatchGameFragment().apply {
				arguments = Bundle().apply {
					putInt(ARG_GAME_INDEX, index)
				}
			}
		}
	}
}