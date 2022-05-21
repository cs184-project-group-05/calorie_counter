package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogCaloriesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Log Calories Page"
    }
    val text: LiveData<String> = _text
}