package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.ucsb.cs.cs184.caloriecounter.R
import edu.ucsb.cs.cs184.caloriecounter.data.User
import edu.ucsb.cs.cs184.caloriecounter.databinding.LogCaloriesFragmentBinding
import edu.ucsb.cs.cs184.caloriecounter.ui.home.HomeViewModel

class LogCaloriesFragment : Fragment() {
    private var _binding: LogCaloriesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var logCaloriesViewModel: LogCaloriesViewModel
    //No idea how to incorporate one-time getter with appRepository. So the following will be done here.
    private val database = Firebase.database
    private var curUID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    private var userRef = database.getReference("users")
    private var isFirstUpdate = true

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        logCaloriesViewModel = ViewModelProvider(this)[LogCaloriesViewModel::class.java]
        _binding = LogCaloriesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        logCaloriesViewModel.getCurUserMutableLiveData().observe(viewLifecycleOwner, Observer {
            logCaloriesViewModel.updateModel(it)
            if(isFirstUpdate){
                // - - - - - - - - - - Draw Meal Inputs from Saved State - - - - - - - - - -
                val numMealInputsInt = logCaloriesViewModel.numMealInputsCreated.value
                if (numMealInputsInt==0) {  // to draw initial meal input
                    logCaloriesViewModel.addMealInputViewModel()
                }
                logCaloriesViewModel.calorieArray.value?.forEachIndexed { index, calorieValue ->
                    if (calorieValue != -1) {
                        if (index == 0) addFirstMealInput(logCaloriesViewModel, calorieValue)
                        else addNewMealInput(logCaloriesViewModel, index, calorieValue)
                    }
                }

                // - - - - - - - - - - Add Meal Input on Button Click - - - - - - - - - -
                val addMealButton = binding.button
                addMealButton.setOnClickListener {  // increases number of meal inputs by 1
                    if (logCaloriesViewModel.numMealInputs.value!! < 5) {
                        logCaloriesViewModel.addMealInputViewModel()
                        val newIndex = logCaloriesViewModel.numMealInputsCreated.value!! - 1
                        addNewMealInput(logCaloriesViewModel, newIndex, 0)
                        if (logCaloriesViewModel.numMealInputs.value!! == 5)
                            binding.button.visibility = View.GONE
                    } else {
                        binding.button.visibility = View.GONE
                    }
                }

                isFirstUpdate = false
            }
            // - - - - - - - - - - Calorie Goal Text - - - - - - - - - -
            val calGoalValue = logCaloriesViewModel.calGoal.value.toString()
            var calGoal1 = getString(R.string.calGoal1_under)
            if (logCaloriesViewModel.goalLoseWeight.value == 0)  // if lose weight == false
                calGoal1 = getString(R.string.calGoal1_over)
            binding.textCalGoal.text = "$calGoal1 $calGoalValue ${getString(R.string.calGoal2)}"
        })

        // - - - - - - - - Make UI changes according to Calorie Count  - - - - - - - - -
        logCaloriesViewModel.calCount.observe(viewLifecycleOwner) {
            // daily calorie count total text
            binding.numCalDaily.text = "$it"

            // indicate in UI when calorie goal is met
            var goalMet = logCaloriesViewModel.calGoal.value!! >= it!!
            if (logCaloriesViewModel.goalLoseWeight.value == 0) goalMet = !goalMet
            if (goalMet) {
                binding.numCalDaily.setTextColor(resources.getColor(R.color.green))
            } else {
                binding.numCalDaily.setTextColor(resources.getColor(R.color.red))
            }
            if (it == 0) {
                binding.numCalDaily.setTextColor(resources.getColor(androidx.appcompat.R.color.material_grey_600))
            }
        }
        return root
    }

    // - - - - - - - - - - Helper Functions - - - - - - - - - -
    private fun addFirstMealInput(logCaloriesViewModel: LogCaloriesViewModel, calorieValue: Int) {
        // generates first meal input element (without delete button), adds to layout
        val mealInputsContainer = binding.linearLayout
        val newInputView: View = layoutInflater.inflate(R.layout.meal_input_1, null)
        val newInput: TextInputEditText = newInputView.findViewById(R.id.mealInput)
        newInput.id = 0
        if (calorieValue != 0) newInput.setText(calorieValue.toString())
        newInput.doAfterTextChanged { handleChangeText(newInput, logCaloriesViewModel, 0) }
        mealInputsContainer.addView(newInputView)
    }
    private fun addNewMealInput(logCaloriesViewModel: LogCaloriesViewModel, index: Int, calorieValue: Int) {
        // generates new meal input element, adds it to layout
        val mealInputsContainer = binding.linearLayout
        val newInputView: View = layoutInflater.inflate(R.layout.meal_input, null)
        val newInput: TextInputEditText = newInputView.findViewById(R.id.mealInput)
        val deleteButton: Button = newInputView.findViewById(R.id.deleteButton)
        newInput.id = index
        if (calorieValue != 0) newInput.setText(calorieValue.toString())
        newInput.doAfterTextChanged { handleChangeText(newInput, logCaloriesViewModel, index) }
        deleteButton.setOnClickListener {  // delete button
            if (index!=0) {
                logCaloriesViewModel.deleteMealInputViewModel(index)
                mealInputsContainer.removeView(newInputView)
                binding.button.visibility = View.VISIBLE
            }
        }
        mealInputsContainer.addView(newInputView)
    }
    private fun handleChangeText(input: TextInputEditText,
                                 logCaloriesViewModel: LogCaloriesViewModel, index: Int) {
        // calculates calorie total on input change
        val strAmount: String = input.text.toString()
        var amount = 0
        if (strAmount != "" && strAmount.length < 10)
            amount = Integer.parseInt(strAmount)
        logCaloriesViewModel.setCalorieI(index, amount)
        logCaloriesViewModel.calculateTotal()
    }
    private fun logValues(logCaloriesViewModel: LogCaloriesViewModel) {
        Log.d("savedValues numMealInput", logCaloriesViewModel.numMealInputs.value.toString())
        Log.d("savedValues numMealInputCreated", logCaloriesViewModel.numMealInputsCreated.value.toString())
        Log.d("savedValues calCount", logCaloriesViewModel.calCount.value.toString())
        Log.d("savedValues calorieArray", logCaloriesViewModel.calorieArray.value.toString())
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}