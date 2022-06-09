package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import edu.ucsb.cs.cs184.caloriecounter.AppRepository
import edu.ucsb.cs.cs184.caloriecounter.data.User

class LogCaloriesViewModel(application: Application) : AndroidViewModel(application) {
    // - - - - - - - - - - member variables - - - - - - - - - -
    private val appRepository = AppRepository(application)
    private var curUserMutableLiveData : MutableLiveData<User> = appRepository.getCurUserMutableLiveData()

    private val _goalLoseWeight = MutableLiveData<Int>()
    private val _calGoal = MutableLiveData<Int>()
    private val _calCount = MutableLiveData<Int>()
    private val _numMealInputs = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _numMealInputsCreated = MutableLiveData<Int>().apply {
        value = 0
    }
    private val _calorieArray = MutableLiveData<MutableList<Int>>().apply {
        value = mutableListOf<Int>()
    }

    val goalLoseWeight: MutableLiveData<Int> = _goalLoseWeight
    val calGoal: MutableLiveData<Int> = _calGoal
    val calCount: MutableLiveData<Int> = _calCount
    val calorieArray: MutableLiveData<MutableList<Int>> = _calorieArray
    val numMealInputs: LiveData<Int> = _numMealInputs
    val numMealInputsCreated: LiveData<Int> = _numMealInputsCreated

    // - - - - - - - - - - public member functions - - - - - - - - - -
    // function updates values from database when called.
    fun updateModel(user: User) {
        _calGoal.value = user.calorie_goal ?: 0
        _calCount.value = user.calorie_count ?: 0
        _numMealInputs.value = user.num_meal_inputs ?: 0
        _numMealInputsCreated.value = user.num_meal_inputs_created ?: 0
        _calorieArray.value = user.calorie_array
        _goalLoseWeight.value = user.goal_lose_weight ?: 0

    }

    // - - - - - - - - - - helper functions - - - - - - - - - -
    fun setCalorieI(i: Int, amount: Int) {   // sets calorieArray[i] = amount
        val newCalorieArray = _calorieArray.value
        newCalorieArray?.set(i,amount)
        _calorieArray.value = newCalorieArray
        appRepository.setCalorieArray(newCalorieArray)
    }

    fun getCurUserMutableLiveData() : MutableLiveData<User>{
        return curUserMutableLiveData
    }

    fun addMealInputViewModel() {  // increases count of meal inputs & adds value to calorieArray
        val newNumInputs = _numMealInputs.value?.plus(1)
        val newNumInputsCreated = _numMealInputsCreated.value?.plus(1)
        val newCalorieArray = _calorieArray.value
        newCalorieArray?.add(0)
        _numMealInputs.value = newNumInputs
        _numMealInputsCreated.value = newNumInputsCreated
        _calorieArray.value = newCalorieArray
        with(appRepository) {
            setNumMealInputs(newNumInputs)
            setNumMealInputsCreated(newNumInputsCreated)
            setCalorieArray(newCalorieArray)
        }
    }
    fun deleteMealInputViewModel(index: Int) {  // decrements meal input count
        val newNumInputs = _numMealInputs.value?.minus(1)
        val newCalorieArray = _calorieArray.value
        newCalorieArray?.set(index,-1)  // -1 denotes deleted input
        _numMealInputs.value = newNumInputs
        _calorieArray.value = newCalorieArray
        appRepository.setNumMealInputs(newNumInputs)
        appRepository.setCalorieArray(newCalorieArray)
        calculateTotal()
    }
    fun calculateTotal() {  // calculates calorie total from calorieArray and sets totalCalories
        var total = 0
        _calorieArray.value?.forEach{ item ->
            if (item != -1) total += item
        }
        _calCount.value = total
        appRepository.setCalorieCount(total)
    }
}