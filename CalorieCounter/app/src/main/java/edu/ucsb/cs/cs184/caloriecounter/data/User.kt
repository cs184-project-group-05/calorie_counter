package edu.ucsb.cs.cs184.caloriecounter.data

data class User(
    var name: String? = null,
    var age: String? = null,
    var weight: String? = null,
    var height: String? = null,
    var gender: String? = null,
    var goal: String? = null,
    var streak: Int? = 1,
    var calorie_count: Int? = 0,
    var calorie_goal: Int? = 0,
    var last_login: String? = null
)
