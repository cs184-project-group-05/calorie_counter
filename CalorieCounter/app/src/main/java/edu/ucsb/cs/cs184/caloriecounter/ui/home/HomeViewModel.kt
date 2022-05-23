package edu.ucsb.cs.cs184.caloriecounter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    fun getWelcomeMessage(): String {
        return "Hello, " + this.name.value
    }

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

    fun getStreakText(): String {
        return "Streak: " + this.streak.value + " days"
    }

}