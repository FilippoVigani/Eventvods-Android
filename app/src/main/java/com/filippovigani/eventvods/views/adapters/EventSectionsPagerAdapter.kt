package com.filippovigani.eventvods.views.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.filippovigani.eventvods.models.Event
import com.filippovigani.eventvods.views.EventSectionFragment

class EventSectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) { //FragmentStatePagerAdapter

	var sections: List<Event.Section>? = null
		set(value){
			field = value
			notifyDataSetChanged()
		}

	override fun getCount(): Int = sections?.size ?: 0

	override fun getItem(position: Int): Fragment? = EventSectionFragment.newInstance(position)

	override fun getItemPosition(obj: Any): Int {
		return POSITION_NONE
	}

	override fun getPageTitle(position: Int): CharSequence? {
		return sections?.get(position)?.title
	}
}