package edu.ucsb.cs.cs184.caloriecounter.ui.home

import android.app.Application
import android.util.Log
import edu.ucsb.cs.cs184.caloriecounter.data.User
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.ucsb.cs.cs184.caloriecounter.AppRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {
    // - - - - - - - - - - member variables - - - - - - - - - -
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

    private val _goalMet = MutableLiveData<Int>()
    val goalMet : MutableLiveData<Int> = _goalMet


    // - - - - - - - - - - getters - - - - - - - - - -

    fun getWelcomeMessage(): String {
        if (this.name.value == null || this.name.value == "") {
            return "Welcome! Please enter your information to get started."
        }
        return "Hello, " + this.name.value
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

    // - - - - - - - - - - public functions - - - - - - - - - -

    fun updateStreakDisplayed(newStreak: Int?) {
        Log.d("newStreak", newStreak.toString())
        streak.value = newStreak?: 0
    }

    // updates all data in the ViewModel once data has loaded.
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
    }

    // function that calculates target daily goal given user input.
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
}