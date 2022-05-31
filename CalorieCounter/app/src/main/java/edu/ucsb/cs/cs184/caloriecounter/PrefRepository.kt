package edu.ucsb.cs.cs184.caloriecounter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PrefRepository(val context: Context) {

    // interface to interact with SharedPreferences
    // which is an exceedingly simple local database used to store key-value pairs
    // no need for Jetpack Room with the amount of information we're storing

    private val pref: SharedPreferences = context.getSharedPreferences(context.resources.getString(R.string.pref), Context.MODE_PRIVATE)

    private val editor = pref.edit()

    private fun String.put(int: Int?) {
        if (int != null) {
            editor.putInt(this, int)
        }
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(array: MutableList<Int>?) {
        // store array as a string
        editor.putString(this, array.toString())
    }

    private fun String.getInt() = pref.getInt(this, 0) // default value returned is 0 if key is not found

    private fun String.getString() = pref.getString(this, null) // default value returned is null if key is not found

    private fun String.getArray(): MutableList<Int>? {
        // get array stored as string -> integer array
        val arrayAsString = pref.getString(this, null)
        return stringToArray(arrayAsString)
    }

    private fun stringToArray(arrStr: String?): MutableList<Int>? {
        if (arrStr != null) {
            Log.d("calorieArray arrStr", arrStr)
        }
        val array = mutableListOf<Int>()
        if (arrStr == null) return array
        val commaSeparatedList = arrStr.substring(1,arrStr.length-1)
        Log.d("calorieArray commaSeparatedList", commaSeparatedList)
        val arrayOfStrings: List<String> = commaSeparatedList.split(", ").toList()
        Log.d("calorieArray arrayOfStrings", arrayOfStrings.toString())
        for (e in arrayOfStrings) {
            array.add(e.toInt())
        }
        Log.d("calorieArray array", array.toString())
        return array
    }

    fun setName(name: String) {
        "name".put(name)
    }

    fun setAge(age: String) {
        "age".put(age)
    }

    fun setWeight(weight: String) {
        "weight".put(weight)
    }

    fun setHeight(height: String) {
        "height".put(height)
    }

    fun setGender(gender: String) {
        "gender".put(gender)
    }

    fun setGoal(goal: String) {
        "goal".put(goal)
    }

    fun setStreak(streak: Int) {
        "streak".put(streak)
    }

    // Log Calories Page Data
    fun setCalorieCount(count: Int) = "calorie_count".put(count)
    fun setCalorieGoal(goal: Int) = "calorie_goal".put(goal)
    fun setNumMealInputs(num: Int?) = "num_meal_inputs".put(num)
    fun setNumMealInputsCreated(num: Int?) = "num_meal_inputs_created".put(num)
    fun setCalorieArray(array: MutableList<Int>?) = "calorie_array".put(array)

    fun setLastLogin(date: String){
        "last_login".put(date)
    }

    fun getName() = "name".getString()

    fun getAge() = "age".getString()

    fun getWeight() = "weight".getString()

    fun getHeight() = "height".getString()

    fun getGender() = "gender".getString()

    fun getGoal() = "goal".getString()

    fun getStreak() = "streak".getInt()

    fun getLastLogin() = "last_login".getString()

    // Log Calories Page Data
    fun getCalorieCount() = "calorie_count".getInt()
    fun getCalorieGoal() = "calorie_goal".getInt()
    fun getNumMealInputs() = "num_meal_inputs".getInt()
    fun getNumMealInputsCreated() = "num_meal_inputs_created".getInt()
    fun getCalorieArray() = "calorie_array".getArray()
}