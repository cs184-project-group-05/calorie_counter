package edu.ucsb.cs.cs184.caloriecounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import edu.ucsb.cs.cs184.caloriecounter.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // - - - - - - - - - - Update Streak - - - - - - - - - -
        updateStreak()

        // - - - - - - - - - - home page title text - - - - - - - - - -
        val title: TextView = binding.textHome
        title.text = homeViewModel.getWelcomeMessage()

        // - - - - - - - - - - name text input field - - - - - - - - - -
        val nameInputView = binding.textInputLayout8
        nameInputView.editText?.setText(homeViewModel.name.value)

        // - - - - - - - - - - age text input field - - - - - - - - - -
        val ageInputView = binding.ageInput
        ageInputView.editText?.setText(homeViewModel.age.value)

        // - - - - - - - - - - weight text input field - - - - - - - - - -
        val weightInputView = binding.weightInput
        weightInputView.editText?.setText(homeViewModel.weight.value)

        // - - - - - - - - - - height text input field - - - - - - - - - -
        val heightInputView = binding.heightInput
        heightInputView.editText?.setText(homeViewModel.height.value)

        // - - - - - - - - - - gender selection dropdown menu input field - - - - - - - - - -
        val genderDropdown = binding.genderDropdown
        genderDropdown.editText?.setText(homeViewModel.gender.value)
        val genders = arrayOf("Male", "Female")
        (genderDropdown.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(genders)

        // - - - - - - - - - - goal selection dropdown menu input field - - - - - - - - - -
        val goalDropdown = binding.goalDropdown
        goalDropdown.editText?.setText(homeViewModel.goal.value)
        val goals = arrayOf("Bulk Up", "Lose Weight")
        (goalDropdown.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(goals)

        // - - - - - - - - - - streak view - - - - - - - - - -
        val streakView: TextView = binding.streakText
        streakView.text = homeViewModel.getStreakText()

        // - - - - - - - - - - fab - - - - - - - - - -
        val fab = binding.extendedFab
        fab.setOnClickListener {
            // set name
            homeViewModel.setName(nameInputView.editText?.text.toString())

            // set age
            val validInputAge = homeViewModel.setAge(ageInputView.editText?.text.toString())

            // set weight
            val validInputWeight = homeViewModel.setWeight(weightInputView.editText?.text.toString())

            // set height
            val validInputHeight = homeViewModel.setHeight(heightInputView.editText?.text.toString())

            // set gender
            homeViewModel.setGender(genderDropdown.editText?.text.toString())

            // set goal
            homeViewModel.setGoal(goalDropdown.editText?.text.toString())

            //set calorie goal
            homeViewModel.setCalGoal(calcGoal())

            // display appropriate snackbar text, also update the title text
            val snackbarText = homeViewModel.getSnackbarText(validInputAge, validInputWeight, validInputHeight)
            Snackbar.make(fab, snackbarText, Snackbar.LENGTH_LONG).show()
            title.text = homeViewModel.getWelcomeMessage()
        }

        return root
    }

    //function that calculates target daily goal given user input.
    //Temporary calculations for now based off of
    private fun calcGoal() : Int{
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val weight : Double = homeViewModel.weight.value!!.toDouble()
        val height : Double = homeViewModel.height.value!!.toDouble()
        val age : Double = homeViewModel.age.value!!.toDouble()

        //could also use setScale if we want to display number at higher precisions.
        val calGoal = if(homeViewModel.gender.value == "Male"){
            (6.23f * weight * 1.15f) + (12.7f*height)-(6.8f*age) + 66f
        }else{
            655 + (4.35 *weight* 1.20) + (4.7 *height)-(4.7 * age)
        }
        return calGoal.toInt()
    }

    //function that updates the streak of the user.
    //checks to see if a day has passed by looking at last and current login time.
    //Temporarily placed here. Could also be placed in main.
    private fun updateStreak(){
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        if(homeViewModel.streak.value!! <= 0){
            homeViewModel.setStreak(1)
        }
        val lastLogin = homeViewModel.lastLogin.value ?: ""
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val c : Calendar = Calendar.getInstance()
        val curDate = sdf.format(c.time)
        c.add(Calendar.DATE, -1)
        val prevDate = sdf.format(c.time)
        if(lastLogin == prevDate){
            homeViewModel.setStreak(homeViewModel.streak.value!! + 1)
        }else if(lastLogin != curDate){
            homeViewModel.setStreak(1)
        }
        homeViewModel.setLastLogin(curDate)
        return
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}