package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        _calGoal.value = prefRepository.getCalorieGoal()
        _calCount.value = prefRepository.getCalorieCount()
        _numMealInputs.value = prefRepository.getNumMealInputs()
        _numMealInputsCreated.value = prefRepository.getNumMealInputsCreated()
        _calorieArray.value = prefRepository.getCalorieArray()
    }

    // - - - - - - - - - - helper functions - - - - - - - - - -
    fun setCalorieI(i: Int, amount: Int) {   // sets calorieArray[i] = amount
        val newCalorieArray = _calorieArray.value
        newCalorieArray?.set(i,amount)
        _calorieArray.value = newCalorieArray
        prefRepository.setCalorieArray(newCalorieArray)
    }
    fun addMealInputViewModel() {  // increases count of meal inputs & adds value to calorieArray
        val newNumInputs = _numMealInputs.value?.plus(1)
        val newNumInputsCreated = _numMealInputsCreated.value?.plus(1)
        val newCalorieArray = _calorieArray.value
        newCalorieArray?.plusAssign(0)
        _numMealInputs.value = newNumInputs
        _numMealInputsCreated.value = newNumInputsCreated
        _calorieArray.value = newCalorieArray
        prefRepository.setNumMealInputs(newNumInputs)
        prefRepository.setNumMealInputsCreated(newNumInputsCreated)
        prefRepository.setCalorieArray(newCalorieArray)
    }
    fun deleteMealInputViewModel(index: Int) {  // decrements meal input count
        val newNumInputs = _numMealInputs.value?.minus(1)
        val newCalorieArray = _calorieArray.value
        newCalorieArray?.set(index,-1)  // -1 denotes deleted input
        Log.d("savedValues newCalorieArray", newCalorieArray.toString())
        _numMealInputs.value = newNumInputs
        _calorieArray.value = newCalorieArray
        prefRepository.setNumMealInputs(newNumInputs)
        prefRepository.setCalorieArray(newCalorieArray)
        calculateTotal()
    }
    fun calculateTotal() {  // calculates calorie total from calorieArray and sets totalCalories
        var total = 0
        _calorieArray.value?.forEach{ item ->
            if (item != -1) total += item
        }
        _calCount.value = total
        prefRepository.setCalorieCount(total)
    }
}