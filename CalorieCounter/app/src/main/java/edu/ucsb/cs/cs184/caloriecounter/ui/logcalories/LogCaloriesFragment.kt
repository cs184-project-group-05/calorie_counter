package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.ucsb.cs.cs184.caloriecounter.R
import edu.ucsb.cs.cs184.caloriecounter.databinding.LogCaloriesFragmentBinding

class LogCaloriesFragment : Fragment() {
    private var _binding: LogCaloriesFragmentBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val logCaloriesViewModel = ViewModelProvider(this).get(LogCaloriesViewModel::class.java)
        _binding = LogCaloriesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // - - - - - - - - - - Calorie Goal Text - - - - - - - - - -
        val calGoalValue = logCaloriesViewModel.calGoal.value.toString()
        binding.textCalGoal.text = "${getString(R.string.calGoal1)} $calGoalValue ${getString(R.string.calGoal2)}"

        // - - - - - - - - - - Daily Total Text - - - - - - - - - -
        logCaloriesViewModel.calCount.observe(viewLifecycleOwner) {
            binding.textDailyCal.text = "${getString(R.string.dailyTotal1)} $it ${getString(R.string.dailyTotal2)}"
        }

        // - - - - - - - - - - Draw Meal Inputs from Saved State - - - - - - - - - -
        val numMealInputsInt = logCaloriesViewModel.numMealInputs.value
        if (numMealInputsInt==0) {  // to draw initial meal input
            logCaloriesViewModel.addMealInputViewModel()
        }
        logCaloriesViewModel.calorieArray.value?.forEachIndexed { index, calorieValue ->
            val newInput = getNewMealInput()
            newInput.id = index
            var inputText = calorieValue.toString()
            if (inputText == "0") inputText = ""
            newInput.setText(inputText)
            newInput.doAfterTextChanged {  // calculates calorie total on input change
                recalculateTotal(newInput, logCaloriesViewModel)
            }
        }

        // - - - - - - - - - - Add Meal Input on Button Click - - - - - - - - - -
        val addMealButton = binding.button
        addMealButton.setOnClickListener {  // increases number of meal inputs by 1
            logCaloriesViewModel.addMealInputViewModel()
            val newInput = getNewMealInput()
            newInput.id = logCaloriesViewModel.numMealInputs.value!! - 1
            newInput.doAfterTextChanged {
                recalculateTotal(newInput, logCaloriesViewModel)
            }
        }
        return root
    }

    private fun recalculateTotal(input: TextInputEditText, homeViewModel: LogCaloriesViewModel) {
        // sets calories[i] = input.text, and updates calorie total
        val strAmount: String = input.text.toString()
        var amount: Int = 0
        if (strAmount != "")
            amount = Integer.parseInt(strAmount)
        homeViewModel.setCalorieI(input.id, amount)
        homeViewModel.calculateTotal()
    }

    private fun getNewMealInput(): TextInputEditText {
        // generates new meal input element, adds it to layout, returns it
        val mealInputsContainer = binding.linearLayout
        val newInputView: View = layoutInflater.inflate(R.layout.meal_input, null)
        val newInput: TextInputEditText = newInputView.findViewById(R.id.mealInput)
        mealInputsContainer.addView(newInputView)
        return newInput
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}