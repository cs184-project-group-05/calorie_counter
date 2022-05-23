package edu.ucsb.cs.cs184.caloriecounter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import edu.ucsb.cs.cs184.caloriecounter.databinding.FragmentHomeBinding

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

        return root
    }

    override fun onDestroyView() {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        // - - - - - - - - - - name text input field - - - - - - - - - -
        val nameInputView = binding.textInputLayout8
        homeViewModel.name.value = nameInputView.editText?.text.toString()
        // TODO: when fragment is destroyed, update the name in local database

        // - - - - - - - - - - age text input field - - - - - - - - - -
        val ageInputView = binding.ageInput
        homeViewModel.age.value = ageInputView.editText?.text.toString()
        // TODO: when fragment is destroyed, update the age in local database

        // - - - - - - - - - - weight text input field - - - - - - - - - -
        val weightInputView = binding.weightInput
        homeViewModel.weight.value = weightInputView.editText?.text.toString()
        // TODO: when fragment is destroyed, update the weight in local database

        // - - - - - - - - - - height text input field - - - - - - - - - -
        val heightInputView = binding.heightInput
        homeViewModel.height.value = heightInputView.editText?.text.toString()
        // TODO: when fragment is destroyed, update the height in local database

        // - - - - - - - - - - gender text input field - - - - - - - - - -
        val genderDropdown = binding.genderDropdown
        homeViewModel.gender.value = genderDropdown.editText?.text.toString()
        // TODO: when fragment is destroyed, update the gender in local database

        // - - - - - - - - - - goal text input field - - - - - - - - - -
        val goalDropdown = binding.goalDropdown
        homeViewModel.goal.value = goalDropdown.editText?.text.toString()
        // TODO: when fragment is destroyed, update the goal in local database

        super.onDestroyView()
        _binding = null
    }
}