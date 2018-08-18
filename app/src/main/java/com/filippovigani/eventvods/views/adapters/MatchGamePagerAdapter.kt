package com.filippovigani.eventvods.views.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import com.filippovigani.eventvods.R
import com.filippovigani.eventvods.models.Match
import com.filippovigani.eventvods.views.EventSectionFragment
import com.filippovigani.eventvods.views.MatchGameFragment

class MatchGamePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) { //FragmentStatePagerAdapter

	var games: List<Match.Game>? = null
		set(value){
			field = value
			notifyDataSetChanged()
		}

	override fun getCount(): Int = games?.size ?: 0

	override fun getItem(position: Int): Fragment? = MatchGameFragment.newInstance(position)

	override fun getItemPosition(obj: Any): Int {
		return games?.indexOf(obj as? Match.Game) ?: PagerAdapter.POSITION_NONE
	}

	override fun getPageTitle(position: Int): CharSequence? {
		return "Game " + (position + 1)
	}
}