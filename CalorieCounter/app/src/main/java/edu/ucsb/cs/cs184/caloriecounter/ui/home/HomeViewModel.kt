package edu.ucsb.cs.cs184.caloriecounter.ui.home

import android.app.Application
import android.util.Log
import edu.ucsb.cs.cs184.caloriecounter.data.User
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.ucsb.cs.cs184.caloriecounter.AppRepository
import edu.ucsb.cs.cs184.caloriecounter.PrefRepository
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(application: Application): AndroidViewModel(application) {

    // - - - - - - - - - - member variables - - - - - - - - - -

    // by lazy = one time initialization: we only want one local database to be created ever

    private val prefRepository = PrefRepository(application)
    private val appRepository = AppRepository(application)
    private var curUserMutableLiveData : MutableLiveData<User> = appRepository.getCurUserMutableLiveData()


    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String> = _name

    private val _age = MutableLiveData<String>()
    val age: MutableLiveData<String> = _age

    private val _weight = MutableLiveData<String>()
    val weight: MutableLiveData<String> = _weight

    private val _height = MutableLiveData<String>()
    val height: MutableLiveData<String> = _height

    private val _gender = MutableLiveData<String>()
    val gender: MutableLiveData<String> = _gender

    private val _goalLoseWeight = MutableLiveData<Int>()
    val goalLoseWeight: MutableLiveData<Int> = _goalLoseWeight

    private val _calCount = MutableLiveData<Int>()
    val calCount: MutableLiveData<Int> = _calCount

    private val _calGoal = MutableLiveData<Int>()
    val calGoal: MutableLiveData<Int> = _calGoal

    private val _streak = MutableLiveData<Int>()
    val streak: MutableLiveData<Int> = _streak

    private val _lastLogin = MutableLiveData<String>()
    val lastLogin: MutableLiveData<String> = _lastLogin

    private val _goalMet = MutableLiveData<Int>()
    val goalMet : MutableLiveData<Int> = _goalMet

    // data locking boolean flags
    private val _canIncreaseStreak = MutableLiveData<Boolean>().apply { value = false }
    private val _canDecreaseStreak = MutableLiveData<Boolean>().apply { value = false }
    private val _canSetNewGoalMet = MutableLiveData<Boolean>().apply { value = false }

    // - - - - - - - - - - getters - - - - - - - - - -

    fun getWelcomeMessage(): String {
        if (this.name.value == null || this.name.value == "") {
            return "Welcome! Please enter your information to get started."
        }
        return "Hello, " + this.name.value
    }

    fun getStreakText(): String {
        return "Streak: " + this.streak.value + " days"
    }

    fun getSnackbarText(
        validInputAge: String?,
        validInputWeight: String?,
        validInputHeight: String?
    ): String {
        return if (validInputAge == null) {
            "Invalid age. Age should be a whole number."
        } else if (validInputWeight == null) {
            "Invalid weight. Weight should be a number."
        } else if (validInputHeight == null) {
            "Invalid height. Height should be a number."
        } else {
            "Your info has been updated!"
        }
    }

    fun getCurUserMutableLiveData() : MutableLiveData<User>{
        return curUserMutableLiveData
    }
    // - - - - - - - - - - setters - - - - - - - - - -

    fun setName(name: String): String {
        appRepository.setName(name)
        this.name.value = name
        return name
    }

    fun setAge(age: String): String? {
        // returns null if the age is invalid, otherwise return the input
        // calls to setAge should check for null return value and handle appropriately
        if (this.isWholeNumber(age)) {
            appRepository.setAge(age)
            this.age.value = age
            return age
        }
        else {
            return null
        }
    }

    fun setWeight(weight: String): String? {
        // returns null if the weight is invalid, otherwise return the weight
        // calls to setWeight should check for null return value and handle appropriately
        if (this.isNumber(weight)) {
            appRepository.setWeight(weight)
            this.weight.value = weight
            return weight
        }
        else {
            return null
        }
    }

    fun setHeight(height: String): String? {
        // returns null if the height is invalid, otherwise return the height
        // calls to setHeight should check for null return value and handle appropriately
        if (this.isNumber(height)) {
            appRepository.setHeight(height)
            this.height.value = height
            return height
        }
        else {
            return null
        }
    }

    fun setGender(gender: String): String {
        appRepository.setGender(gender)
        this.gender.value = gender
        return gender
    }

    fun setGoalLoseWeight(goal: Int): Int {
        appRepository.setGoalLoseWeight(goal)
        this.goalLoseWeight.value = goal
        return goal
    }

    fun setCalGoal(calGoal: Int): Int{
        appRepository.setCalorieGoal(calGoal)
        return calGoal
    }

    fun setStreak(streak: Int): Int{
        appRepository.setStreak(streak)
        this.streak.value = streak
        return streak
    }

    fun setLastLogin(lastLogin: String): String{
        appRepository.setLastLogin(lastLogin)
        this.lastLogin.value = lastLogin
        return lastLogin
    }

    // - - - - - - - - - - public functions - - - - - - - - - -

    //updates all data in the ViewModel once data has loaded.
    fun updateModel(user: User){
        name.value = user.name ?: ""
        age.value = user.age ?: ""
        weight.value = user.weight ?: ""
        height.value = user.height ?: ""
        gender.value = user.gender ?: ""

        goalLoseWeight.value = user.goal_lose_weight ?: 0
        goalMet.value = user.goal_met ?: 0

        calCount.value = user.calorie_count ?: 0
        calGoal.value = user.calorie_goal ?: 0

        streak.value = user.streak ?: 0
        lastLogin.value = user.last_login ?: ""
    }

    //function that calculates target daily goal given user input.
    fun calcGoal() : Int{
        //returns 0 if values aren't available.
        if(this.weight.value == "" || this.weight.value == null ||
            this.height.value == "" || this.weight.value == null ||
            this.age.value == "" || this.age.value == null ||
            this.gender.value == null){
            return 0
        }
        val weight : Double = this.weight.value!!.toDouble()
        val height : Double = this.height.value!!.toDouble()
        val age : Double = this.age.value!!.toDouble()

        //could also use setScale if we want to display number at higher precisions.
        val calGoal = if(this.gender.value == "Male"){
            (6.23f * weight * 1.15f) + (12.7f*height)-(6.8f*age) + 66f
        }else{
            655 + (4.35 *weight* 1.20) + (4.7 *height)-(4.7 * age)
        }
        return calGoal.toInt()
    }

    /*function that checks if the current date is different from the user's previous login
    and updates various features based on date changes. This includes Streak and Meal Logs.
    */
    fun updateDate(){
        val lastLogin = this.lastLogin.value ?: ""
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val c : Calendar = Calendar.getInstance()
        val curDate = sdf.format(c.time)

        if (lastLogin != curDate){
            c.add(Calendar.DATE, -1)

            // if goal not met on previous day, set streak to 0
            val goalMet = this.goalMet.value
            if (goalMet == 0) {
                this.setStreak(0)
            }

            // unlock data changes
            _canDecreaseStreak.value = false
            _canIncreaseStreak.value = true
            _canSetNewGoalMet.value = true

            c.add(Calendar.DATE, 1) // reset calendar back to original position

            // reset calorie UI data
            appRepository.setCalorieCount(0)
            appRepository.setNumMealInputs(0)
            appRepository.setNumMealInputsCreated(0)
            appRepository.setCalorieArray(mutableListOf<Int>())
            appRepository.setGoalMet(0)  // set to 1 when goal is met
        }

        this.setLastLogin(curDate) //change last login to the current date.
    }

    // - - - - - - - - - - private helper functions - - - - - - - - - -

    private fun isWholeNumber(s: String): Boolean {
        // returns True if every character in the string is a digit
        // ie. returns false for negative numbers and if a decimal is present
        return s.all { char -> char.isDigit() }
    }

    private fun isNumber(s: String): Boolean {
        // returns True if every character in the string is a digit or the period character "."
        // ie. returns false for negative numbers
        return s.all {char -> char.isDigit() || char == '.'}
    }

    // function that updates the streak of the user.
    fun updateStreak() {
        if (_canSetNewGoalMet.value == false) return
        if (this.streak.value == null || this.streak.value!! < 0) this.setStreak(0)

        var goalMet = (this.calGoal.value ?: 0) >= (this.calCount.value ?: 0)
        if (this.goalLoseWeight.value == 0) goalMet = !goalMet

        if (goalMet && (this.calCount.value ?: 0) >= 1) {
            // only allow for one streak increase per day
            if (_canIncreaseStreak.value == true) {
                this.setStreak(this.streak.value!! + 1)
                _canIncreaseStreak.value = false
                _canDecreaseStreak.value = true  // can decrease, since already increased
            }
            appRepository.setGoalMet(1)
        }
        else {
            // only allow for one streak decrease after a streak increase
            if (_canDecreaseStreak.value == true) {
                this.setStreak(this.streak.value!! - 1)
                _canDecreaseStreak.value = false
                _canIncreaseStreak.value = true
            }
            appRepository.setGoalMet(0)
        }
    }
}