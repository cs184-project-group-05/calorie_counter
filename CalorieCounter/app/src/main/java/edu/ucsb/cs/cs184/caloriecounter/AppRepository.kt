package edu.ucsb.cs.cs184.caloriecounter

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.ucsb.cs.cs184.caloriecounter.data.User

class AppRepository (application: Application){
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var database: FirebaseDatabase = Firebase.database
    private var userRef = database.getReference("users")
    private var curUserMutableLiveData : MutableLiveData<User> = MutableLiveData()
    private var curUID = firebaseAuth.currentUser?.uid ?: "" //Jank solution for circumventing null uid
    private lateinit var userData: User

    init{ //attach a listener upon creation so that it updates whenever data is updated.
        val userListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var results : HashMap<String, Any>? = dataSnapshot.value as? HashMap<String, Any>
                userData = User(
                    name = results?.get("name") as String?,
                    age = results?.get("age") as String?,
                    weight = results?.get("weight") as String?,
                    height = results?.get("height") as String?,
                    gender = results?.get("gender") as String?,

                    goal_lose_weight = (results?.get("goal_lose_weight") as Long?)?.toInt() ?: 0,
                    goal_met = (results?.get("goal_met") as Long?)?.toInt() ?: 0,

                    calorie_count = (results?.get("calorie_count") as Long?)?.toInt() ?: 0,
                    calorie_goal = (results?.get("calorie_goal") as Long?)?.toInt() ?:0,

                    num_meal_inputs = (results?.get("num_meal_inputs") as Long?)?.toInt() ?:0,
                    num_meal_inputs_created = (results?.get("num_meal_inputs_created") as Long?)?.toInt() ?:0,
                    calorie_array = listLongToInt(results?.get("calorie_array") as MutableList<Long>?),

                    streak = (results?.get("streak") as Long?)?.toInt() ?: 0,
                    last_login = results?.get("last_login") as String?,

                    history = (results?.get("history") as MutableList<String>?)
                )
                curUserMutableLiveData.postValue(userData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException())
            }
        }
        //attach listener that updates the current user's data
        userRef.child(curUID).addValueEventListener(userListener)
    }

    // - - - - - - - - - - - Helper Functions - - - - - - - - - - - - >
    private fun listLongToInt(list : MutableList<Long>?) : MutableList<Int>{
        val result = mutableListOf<Int>()
        if(list != null){
            for (longVal in list){
                result.add(longVal.toInt())
            }
        }
        return result
    }

    fun setUser(user: User){
        userRef.child(curUID).setValue(user)
    }
    fun setName(name: String) {
        userRef.child(curUID).child("name").setValue(name)
    }

    fun setAge(age: String) {
        userRef.child(curUID).child("age").setValue(age)
    }

    fun setWeight(weight: String) {
        userRef.child(curUID).child("weight").setValue(weight)
    }

    fun setHeight(height: String) {
        userRef.child(curUID).child("height").setValue(height)
    }

    fun setGender(gender: String) {
        userRef.child(curUID).child("gender").setValue(gender)
    }

    // - - - - - - - - Calorie Related - - - - - - - - - - ->
    fun setGoalLoseWeight(goal: Int) {
        userRef.child(curUID).child("goal_lose_weight").setValue(goal)
    }

    fun setGoalMet(goal: Int) {
        userRef.child(curUID).child("goal_met").setValue(goal)
    }

    fun setNumMealInputs(num: Int?) {
        userRef.child(curUID).child("num_meal_inputs").setValue(num)
    }

    fun setNumMealInputsCreated(num: Int?){
        userRef.child(curUID).child("num_meal_inputs_created").setValue(num)
    }

    fun setCalorieArray(array: MutableList<Int>?){
        userRef.child(curUID).child("calorie_array").setValue(array)
    }

    fun setCalorieCount(count: Int){
        userRef.child(curUID).child("calorie_count").setValue(count)
    }

    fun setCalorieGoal(goal: Int){
        userRef.child(curUID).child("calorie_goal").setValue(goal)
    }

    fun setStreak(streak: Int) {
        userRef.child(curUID).child("streak").setValue(streak)
    }

    fun setLastLogin(date: String){
        userRef.child(curUID).child("last_login").setValue(date)
    }

    fun getCurUserMutableLiveData() : MutableLiveData<User>{
        return curUserMutableLiveData
    }

    fun getName() : String?{
        return curUserMutableLiveData.value?.name
    }

    // - - - - - - - - Calendar - - - - - - - -

    fun setHistory(array: MutableList<String>?) {
        userRef.child(curUID).child("history").setValue(array)
    }

}