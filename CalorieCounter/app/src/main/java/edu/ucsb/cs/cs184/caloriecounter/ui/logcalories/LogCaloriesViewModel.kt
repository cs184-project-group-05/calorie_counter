package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.ucsb.cs.cs184.caloriecounter.PrefRepository

class LogCaloriesViewModel(application: Application) : AndroidViewModel(application) {
    // - - - - - - - - - - member variables - - - - - - - - - -
    private val prefRepository by lazy { PrefRepository(application) }
    private val _calGoal = MutableLiveData<Int>().apply{
        value = prefRepository.getCalorieGoal()
    }
    private val _calCount = MutableLiveData<Int>().apply{
        value = 0
    }
    private val _numMealInputs = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _numMealInputsCreated = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _calorieArray = MutableLiveData<MutableList<Int>>().apply {
        value = mutableListOf<Int>()
    }

    val calGoal: MutableLiveData<Int> = _calGoal
    val calCount: MutableLiveData<Int> = _calCount
    val calorieArray: MutableLiveData<MutableList<Int>> = _calorieArray
    val numMealInputs: LiveData<Int> = _numMealInputs
    val numMealInputsCreated: LiveData<Int> = _numMealInputsCreated

    // - - - - - - - - - - public member functions - - - - - - - - - -
    //function updates values from database when called.
    fun update() {
        this.calGoal.value = prefRepository.getCalorieGoal()
    }

    // - - - - - - - - - - helper functions - - - - - - - - - -
    fun setCalorieI(i: Int, amount: Int) {   // sets calorieArray[i] = amount
        _calorieArray.value?.set(i, amount)
    }
    fun addMealInputViewModel() {  // increases count of meal inputs & adds value to calorieArray
        _numMealInputsCreated.value = _numMealInputsCreated.value?.plus(1)
        _numMealInputs.value = _numMealInputs.value?.plus(1)
        _calorieArray.value?.add(0)
    }
    fun deleteMealInputViewModel(index: Int) {  // decrements meal input count
        _numMealInputs.value = _numMealInputs.value?.minus(1)
        _calorieArray.value?.set(index, -1)  // -1 denotes deleted input
        calculateTotal()
    }
    fun calculateTotal() {  // calculates calorie total from calorieArray and sets totalCalories
        var total: Int = 0
        _calorieArray.value?.forEach{ item ->
            if (item != -1) total += item
        }
        _calCount.value = total
    }
}