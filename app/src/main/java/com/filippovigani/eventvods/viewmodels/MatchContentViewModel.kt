package com.filippovigani.eventvods.viewmodels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.filippovigani.eventvods.BR
import com.filippovigani.eventvods.models.Match

class MatchContentViewModel(val match: Match) : ViewModel(){
	var revealed: ObservableBoolean = ObservableBoolean(false)

	val spoilerable
		get() = match.spoiler1 || match.spoiler2

	fun reveal(){
		revealed.set(true)
	}
}