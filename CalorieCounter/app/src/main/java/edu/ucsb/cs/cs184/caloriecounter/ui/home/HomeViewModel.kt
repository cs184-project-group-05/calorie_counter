package edu.ucsb.cs.cs184.caloriecounter.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import edu.ucsb.cs.cs184.caloriecounter.PrefRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {

    // - - - - - - - - - - member variables - - - - - - - - - -

    // by lazy = one time initialization: we only want one local database to be created ever
    private val prefRepository by lazy { PrefRepository(application) }

    private val _name = MutableLiveData<String>().apply {
        value = prefRepository.getName()
    }
    val name: MutableLiveData<String> = _name

    private val _age = MutableLiveData<String>().apply {
        value = prefRepository.getAge()
    }
    val age: MutableLiveData<String> = _age

    private val _weight = MutableLiveData<String>().apply {
        value = prefRepository.getWeight()
    }
    val weight: MutableLiveData<String> = _weight

    private val _height = MutableLiveData<String>().apply {
        value = prefRepository.getHeight()
    }
    val height: MutableLiveData<String> = _height

    private val _gender = MutableLiveData<String>().apply {
        value = prefRepository.getGender()
    }
    val gender: MutableLiveData<String> = _gender

    private val _goal = MutableLiveData<String>().apply {
        value = prefRepository.getGoal()
    }
    val goal: MutableLiveData<String> = _goal

    private val _streak = MutableLiveData<Int>().apply {
        value = prefRepository.getStreak()
    }
    val streak: MutableLiveData<Int> = _streak

    private val _lastLogin = MutableLiveData<String>().apply{
        value = prefRepository.getLastLogin()
    }
    val lastLogin: MutableLiveData<String> = _lastLogin

    // - - - - - - - - - - getters - - - - - - - - - -

    fun getWelcomeMessage(): String {
        if (this.name.value == null) {
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

    // - - - - - - - - - - setters - - - - - - - - - -

    fun setName(name: String): String {
        prefRepository.setName(name)
        this.name.value = name
        return name
    }

    fun setAge(age: String): String? {
        // returns null if the age is invalid, otherwise return the input
        // calls to setAge should check for null return value and handle appropriately
        if (this.isWholeNumber(age)) {
            prefRepository.setAge(age)
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
            prefRepository.setWeight(weight)
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
            prefRepository.setHeight(height)
            this.height.value = height
            return height
        }
        else {
            return null
        }
    }

    fun setGender(gender: String): String {
        prefRepository.setGender(gender)
        this.gender.value = gender
        return gender
    }

    fun setGoal(goal: String): String {
        prefRepository.setGoal(goal)
        this.goal.value = goal
        return goal
    }

    fun setCalGoal(calGoal: Int): Int{
        prefRepository.setCalorieGoal(calGoal)
        return calGoal
    }

    fun setStreak(streak: Int): Int{
        prefRepository.setStreak(streak)
        this.streak.value = streak
        return streak
    }

    fun setLastLogin(lastLogin: String): String{
        prefRepository.setLastLogin(lastLogin)
        this.lastLogin.value = lastLogin
        return lastLogin
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