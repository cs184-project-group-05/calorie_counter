package edu.ucsb.cs.cs184.caloriecounter.ui.leaderboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.ucsb.cs.cs184.caloriecounter.AppRepository
import edu.ucsb.cs.cs184.caloriecounter.data.UserStreak

class LeaderboardViewModel (application: Application) : AndroidViewModel(application){
    private val appRepository = AppRepository(application)
    private var curStreaksMutableLiveData: MutableLiveData<ArrayList<UserStreak>> =
        appRepository.getCurStreaksMutableLiveData()

    fun getCurStreaksMutableLiveData(): MutableLiveData<ArrayList<UserStreak>> {
        return curStreaksMutableLiveData
    }
    fun updateLeaderboard() {
        curStreaksMutableLiveData = appRepository.getCurStreaksMutableLiveData()
//        Log.d("leaderboard", curStreaksMutableLiveData.value.toString())
    }
}