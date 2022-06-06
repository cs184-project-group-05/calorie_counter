package edu.ucsb.cs.cs184.caloriecounter.data

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*
import edu.ucsb.cs.cs184.caloriecounter.AppRepository

class StreakData(application: Application): AndroidViewModel(application) {
    private val appRepository = AppRepository(application)

    // data locking boolean flags
    private val _canIncreaseStreak = MutableLiveData<Boolean>().apply { value = false }
    private val _canDecreaseStreak = MutableLiveData<Boolean>().apply { value = false }
    private val _canSetNewGoalMet = MutableLiveData<Boolean>().apply { value = false }

    fun setHistory(lastLogin: String, metGoal: Int, user: User) {
        // each item has the format "dd-mm-yyyy:{0/1}", eg. 01-06-2022:1 means met goal on June 1st 2022
        val entry = "$lastLogin:$metGoal"
        var newHistory = user.history
        if (newHistory == null) newHistory = mutableListOf(entry)
        else newHistory.add(entry)
        appRepository.setHistory(newHistory)
    }

    // fetches login data and updates date & goalMet, if streak reset returns true
    @SuppressLint("SimpleDateFormat")
    fun updateDate(user: User):Boolean {
        val lastLogin = user.last_login?: ""
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val c : Calendar = Calendar.getInstance()
        val curDate = sdf.format(c.time)
        var returnVal = false

        if (lastLogin != curDate) {
            c.add(Calendar.DATE, -1)

            // if goal not met on previous day, set streak to 0
            val goalMet = user.goal_met
            if (goalMet == 0) {
                appRepository.setStreak(0)
                returnVal = true
            }

            // add entry to calendar
            if (goalMet != null) setHistory(sdf.format(c.time), goalMet, user)

            c.add(Calendar.DATE, 1) // reset calendar back to original position

            // unlock data changes
            _canDecreaseStreak.value = false
            _canIncreaseStreak.value = true
            _canSetNewGoalMet.value = true

            // reset calorie UI data
            appRepository.setCalorieCount(0)
            appRepository.setNumMealInputs(0)
            appRepository.setNumMealInputsCreated(0)
            appRepository.setCalorieArray(mutableListOf<Int>())
            appRepository.setGoalMet(0)  // set to 1 when goal is met
        }
        appRepository.setLastLogin(curDate) // change last login to the current date.
        return returnVal
    }

    // fetches calorie data and updates streak, returns value
    fun updateStreak(user: User, streakReset: Boolean): Int? {
        val streak = user.streak
        var newStreak = streak
        if (streakReset) newStreak = 0
        val calGoal = user.calorie_goal
        val calCount = user.calorie_count
        val goalLoseWeight = user.goal_lose_weight
        if (_canSetNewGoalMet.value == false) return newStreak
        if (streak == null || streak < 0) appRepository.setStreak(0)

        var goalMet = (calGoal ?: 0) >= (calCount ?: 0)
        if (goalLoseWeight == 0) goalMet = !goalMet

        if (goalMet && (calCount ?: 0) >= 1) {
            // only allow for one streak increase per day
            if (_canIncreaseStreak.value == true) {
                if (streak != null) newStreak = streak + 1
                else newStreak = 1
                _canIncreaseStreak.value = false
                _canDecreaseStreak.value = true  // can decrease, since already increased
            }
            appRepository.setGoalMet(1)
        }
        else {
            // only allow for one streak decrease after a streak increase
            if (_canDecreaseStreak.value == true) {
                if (streak != null) newStreak = streak - 1
                else newStreak = 0
                _canDecreaseStreak.value = false
                _canIncreaseStreak.value = true
            }
            appRepository.setGoalMet(0)
        }
        appRepository.setStreak(newStreak?: 0)
        return newStreak
    }
}