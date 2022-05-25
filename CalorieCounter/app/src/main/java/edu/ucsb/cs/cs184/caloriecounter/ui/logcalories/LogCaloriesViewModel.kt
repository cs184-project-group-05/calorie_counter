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
    val calGoal: MutableLiveData<Int> = _calGoal

    private val _calCount = MutableLiveData<Int>().apply{
        value = prefRepository.getCalorieCount()
    }
    val calCount: MutableLiveData<Int> = _calCount

    // - - - - - - - - - - public member functions - - - - - - - - - -
    //function updates values from database when called.
    fun update(){
        this.calGoal.value = prefRepository.getCalorieGoal()
        this.calCount.value = prefRepository.getCalorieGoal()
    }
}