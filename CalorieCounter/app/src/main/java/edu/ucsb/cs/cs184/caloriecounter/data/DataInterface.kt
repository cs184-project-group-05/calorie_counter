package edu.ucsb.cs.cs184.caloriecounter.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.ucsb.cs.cs184.caloriecounter.R
import edu.ucsb.cs.cs184.caloriecounter.data.User

class DataInterface() {

    // interface to interact with SharedPreferences
    // which is an exceedingly simple local database used to store key-value pairs
    // no need for Jetpack Room with the amount of information we're storing
    private val database = Firebase.database
    private var userId = FirebaseAuth.getInstance().currentUser!!.uid
    private var userRef = database.getReference("users").child(userId)


    fun setName(name: String) {
        userRef.child("name").setValue(name)
    }

    fun setAge(age: String) {
        userRef.child("age").setValue(age)
    }

    fun setWeight(weight: String) {
        userRef.child("weight").setValue(weight)
    }

    fun setHeight(height: String) {
        userRef.child("height").setValue(height)
    }

    fun setGender(gender: String) {
        userRef.child("gender").setValue(gender)
    }

    fun setGoal(goal: String) {
        userRef.child("goal").setValue(goal)
    }

    fun setStreak(streak: Int) {
        userRef.child("streak").setValue(streak)
    }

    fun setCalorieCount(count: Int){
        userRef.child("calorie_count").setValue(count)
    }

    fun setCalorieGoal(goal: Int){
        userRef.child("goal").setValue(goal)
    }

    fun setLastLogin(date: String){
        userRef.child("last_login").setValue(date)
    }

    fun getName() : String {
        val dataSnapshot = userRef.child("name").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }

    fun getAge() : String {
        val dataSnapshot = userRef.child("age").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }

    fun getWeight() : String {
        val dataSnapshot = userRef.child("weight").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }

    fun getHeight() : String {
        val dataSnapshot = userRef.child("height").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }

    fun getGender() : String {
        val dataSnapshot = userRef.child("gender").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }

    fun getGoal() : String {
        val dataSnapshot = userRef.child("goal").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }

    fun getStreak() : Int {
        val dataSnapshot = userRef.child("streak").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as Int
    }

    fun getCalorieCount() : Int {
        val dataSnapshot = userRef.child("calorie_count").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as Int
    }

    fun getCalorieGoal() : Int {
        val dataSnapshot = userRef.child("calorie_goal").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as Int
    }

    fun getLastLogin() : String {
        val dataSnapshot = userRef.child("date").get().addOnSuccessListener {
            it.value
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        return dataSnapshot as String
    }
}