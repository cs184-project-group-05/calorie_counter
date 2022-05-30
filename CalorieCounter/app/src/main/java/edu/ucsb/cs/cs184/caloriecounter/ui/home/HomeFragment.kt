package edu.ucsb.cs.cs184.caloriecounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import edu.ucsb.cs.cs184.caloriecounter.R
import edu.ucsb.cs.cs184.caloriecounter.SignIn
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

        // - - - - - - - - - - Update Streak and other values when new day is detected - - - - - - - - - -
        homeViewModel.updateDate()

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
            homeViewModel.setCalGoal(homeViewModel.calcGoal())

            // display appropriate snackbar text, also update the title text
            val snackbarText = homeViewModel.getSnackbarText(validInputAge, validInputWeight, validInputHeight)
            Snackbar.make(fab, snackbarText, Snackbar.LENGTH_LONG).show()
            title.text = homeViewModel.getWelcomeMessage()
        }

        // Sign out of app and restart the activity
        val button = binding.button3
        button.setOnClickListener {
            Firebase.auth.signOut()
            getActivity()?.recreate();
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}