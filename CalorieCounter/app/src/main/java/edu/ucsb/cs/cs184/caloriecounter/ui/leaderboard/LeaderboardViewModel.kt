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
    fun getSortedData(): List<StreakItemViewModel> {
        val data = ArrayList<StreakItemViewModel>()
        curStreaksMutableLiveData.value?.forEachIndexed { _, userStreak ->
            if (userStreak.name != null) {
                data.add(
                    StreakItemViewModel(
                        userStreak.name.toString(),
                        userStreak.streak.toString()
                    )
                )
            }
        }
        return data.sortedByDescending { item -> item.streak.toInt() }
    }
}