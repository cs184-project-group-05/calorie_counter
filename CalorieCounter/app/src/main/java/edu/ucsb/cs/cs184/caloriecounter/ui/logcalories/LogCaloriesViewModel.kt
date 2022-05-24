package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogCaloriesViewModel : ViewModel() {
    private val _numMealInputs = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _totalCalories = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _calorieArray = MutableLiveData<MutableList<Int>>().apply {
        value = mutableListOf<Int>()
    }

    val totalCalories: LiveData<Int> = _totalCalories
    val numMealInputs: LiveData<Int> = _numMealInputs

    fun setCalorieI(i: Int, amount: Int) {
        // sets calorieArray[i] = amount
        _calorieArray.value?.set(i, amount)
    }
    fun addMealInputViewModel() {
        // increases count of meal inputs & adds value to calorieArray
        _numMealInputs.value = _numMealInputs.value?.plus(1)
        _calorieArray.value?.add(0)
    }
    fun calculateTotal() {
        // calculates calorie total from calorieArray and sets totalCalories
        var total: Int = 0
        _calorieArray.value?.forEach{ item ->
            total += item
        }
        _totalCalories.value = total
    }
}