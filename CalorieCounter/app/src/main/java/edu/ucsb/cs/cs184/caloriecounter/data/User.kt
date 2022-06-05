package edu.ucsb.cs.cs184.caloriecounter.data

data class User(
    var name: String? = null,
    var age: String? = null,
    var weight: String? = null,
    var height: String? = null,
    var gender: String? = null,

    //- - - - - -  calorie related - - - - - - >
    var goal_lose_weight: Int? = 0,
    var goal_met: Int? = 0,

    var calorie_count: Int? = 0,
    var calorie_goal: Int? = 0,

    var num_meal_inputs: Int? = 0,
    var num_meal_inputs_created: Int? = 0,
    var calorie_array: MutableList<Int>? = null,
    // - - - - - - - date related - - - - - - - >
    var streak: Int? = 0,
    var last_login: String? = null,
    val history: MutableList<String>? = null
)
