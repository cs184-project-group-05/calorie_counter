package edu.ucsb.cs.cs184.caloriecounter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // - - - - - - - - - - member variables - - - - - - - - - -

    private val _name = MutableLiveData<String>().apply {
        value = null
    }
    val name: MutableLiveData<String> = _name

    private val _age = MutableLiveData<String>().apply {
        value = null
    }
    val age: MutableLiveData<String> = _age

    private val _weight = MutableLiveData<String>().apply {
        value = null
    }
    val weight: MutableLiveData<String> = _weight

    private val _height = MutableLiveData<String>().apply {
        value = null
    }
    val height: MutableLiveData<String> = _height

    private val _gender = MutableLiveData<String>().apply {
        value = null
    }
    val gender: MutableLiveData<String> = _gender

    private val _goal = MutableLiveData<String>().apply {
        value = null
    }
    val goal: MutableLiveData<String> = _goal

    private val _streak = MutableLiveData<Int>().apply {
        value = 0
    }
    val streak: MutableLiveData<Int> = _streak

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
        // TODO: update the name in local database
        this.name.value = name
        return name
    }

    fun setAge(age: String): String? {
        // returns null if the age is invalid, otherwise return the input
        // calls to setAge should check for null return value and handle appropriately
        if (this.isWholeNumber(age)) {
            // TODO: update the age in local database
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
            // TODO: update the weight in local database
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
            // TODO: update the height in local database
            this.height.value = height
            return height
        }
        else {
            return null
        }
    }

    fun setGender(gender: String): String {
        // TODO: update the gender in local database
        this.gender.value = gender
        return gender
    }

    fun setGoal(goal: String): String {
        // TODO: update the goal in local database
        this.goal.value = goal
        return goal
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