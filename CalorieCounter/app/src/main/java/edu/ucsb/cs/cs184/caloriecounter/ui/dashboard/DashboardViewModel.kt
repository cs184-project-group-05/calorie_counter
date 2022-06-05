package edu.ucsb.cs.cs184.caloriecounter.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.ucsb.cs.cs184.caloriecounter.AppRepository
import edu.ucsb.cs.cs184.caloriecounter.data.User
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.*

class DashboardViewModel(application: Application): AndroidViewModel(application) {

    // - - - - - - - - - - internal classes - - - - - - - - - -

    class Indicator (
        override val date: CalendarDate,
        override val color: Int
    ): CalendarView.DateIndicator

    // - - - - - - - - - - member variables - - - - - - - - - -

    private val appRepository = AppRepository(application)
    private var curUserMutableLiveData : MutableLiveData<User> = appRepository.getCurUserMutableLiveData()

    private val monthMap = mapOf(
        "01" to Calendar.JANUARY,
        "02" to Calendar.FEBRUARY,
        "03" to Calendar.MARCH,
        "04" to Calendar.APRIL,
        "05" to Calendar.MAY,
        "06" to Calendar.JUNE,
        "07" to Calendar.JULY,
        "08" to Calendar.AUGUST,
        "09" to Calendar.SEPTEMBER,
        "10" to Calendar.OCTOBER,
        "11" to Calendar.NOVEMBER,
        "12" to Calendar.DECEMBER
    )

    private val calendar = Calendar.getInstance()

//    private val _indicators = MutableLiveData<MutableList<String>>().apply {
//        // TODO: delete this example data and load firebase data instead
//        // value = listOf(Pair("05/12/2022", true), Pair("05/21/2022", false), Pair("05/25/2022", true))
//        value = curUserMutableLiveData.value?.history
//    }
    private val _indicators = MutableLiveData<MutableList<String>>()
    val indicators : MutableLiveData<MutableList<String>> = _indicators

    // - - - - - - - - - - getters - - - - - - - - - -

    fun getInitialDate(): CalendarDate {
        // the calendar will start displaying at the month cooresponding to the initial date
        // eg. if initial date = May 5th, 2022, then calendar will start at May 2022
        // so initial date should be the current day
        return CalendarDate(this.calendar.time)
    }

    fun getSelectionMode(): CalendarView.SelectionMode {
        // do not allow the user to select and highlight sections of calendar
        return CalendarView.SelectionMode.NONE
    }

    fun getFirstDayOfWeek(): Int {
        // calendars in the US usually have Sunday as the first day of the week
        return Calendar.SUNDAY
    }

    fun getShowYearSelectionView(): Boolean {
        // show arrow buttons that move the calendar forwards/backwards 1 year
        return true
    }

    fun getCurUserMutableLiveData() : MutableLiveData<User> {
        return this.curUserMutableLiveData
    }

    fun getIndicatorInformation(): MutableList<Pair<CalendarDate, Boolean>> {
        // return a list of pairs
        // pair.first = a CalendarDate representing a date
        // pair.second = a Boolean representing whether the user met their goal that day or not
        // does not return a list of indicators because we need to requireContext() inside the fragment
        // so a private function in the fragment uses this information to build the list of indicator objects
        if (this.indicators.value == null) {
            return mutableListOf()
        }
        else {
            val ret: MutableList<Pair<CalendarDate, Boolean>> = mutableListOf()
            for (item in this.indicators.value!!) {
                // for each pair,
                // pair.first is a date string of the format "dd/mm/yyyy"
                // pair.second is a boolean - true means the user reached their goal (green indicator) and false is the opposite
                // if the user did not check in that day, there will be no indicator for that day
                val entryAsList = item.split(":")
                val metGoal = entryAsList[1] == "1"
                val dateAsList = entryAsList[0].split("-")
                val day = dateAsList[0].toInt()
                val month = monthMap[dateAsList[1]]!!
                val year = dateAsList[2].toInt()
                this.calendar.set(year, month, day)
                val date = CalendarDate(this.calendar.time)
                ret.add(Pair(date, metGoal))
            }
            return ret
        }
    }

    // - - - - - - - - - - public functions - - - - - - - - - -

    fun updateModel(user: User) {
        this.indicators.value = user.history
    }

}