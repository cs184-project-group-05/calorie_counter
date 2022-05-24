package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogCaloriesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Log Calories Page"
    }
    private val _numMealInputs = MutableLiveData<Int>().apply {
        value = 1
    }
    fun addMealInput() {
        _numMealInputs.value = _numMealInputs.value?.plus(1)
    }
    val text: LiveData<String> = _text
    val numMealInputs: LiveData<Int> = _numMealInputs
}