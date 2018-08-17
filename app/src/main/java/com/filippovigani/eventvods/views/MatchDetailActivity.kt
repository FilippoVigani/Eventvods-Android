package com.filippovigani.eventvods.views

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.MenuItem
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.databinding.ActivityMatchDetailBinding
import com.filippovigani.eventvods.viewmodels.EventDetailViewModel
import com.filippovigani.eventvods.viewmodels.MatchContentViewModel
import com.filippovigani.eventvods.viewmodels.MatchDetailViewModel
import kotlinx.android.synthetic.main.activity_event_detail.*

class MatchDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val binding: ActivityMatchDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_match_detail)
		binding.setLifecycleOwner(this)
		intent.getStringExtra(MatchDetailActivity.ARG_MATCH_ID)?.let { matchId ->
			binding.viewModel = ViewModelProviders.of(this, MatchDetailViewModel.Factory(matchId)).get(MatchDetailViewModel::class.java)
		}

		setSupportActionBar(detail_toolbar)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	override fun onOptionsItemSelected(item: MenuItem) =
		when (item.itemId) {
			android.R.id.home -> {
				finishAfterTransition()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}

	companion object {
		const val ARG_MATCH_ID = "match_id"
	}
}
