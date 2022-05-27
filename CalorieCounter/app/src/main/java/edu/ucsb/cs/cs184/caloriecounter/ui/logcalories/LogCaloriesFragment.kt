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
        val logCaloriesViewModel = ViewModelProvider(this)[LogCaloriesViewModel::class.java]
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
            if (calorieValue != -1) {
                if (index == 0) addFirstMealInput(logCaloriesViewModel)
                else addNewMealInput(logCaloriesViewModel, index)
            }
        }

        // - - - - - - - - - - Add Meal Input on Button Click - - - - - - - - - -
        val addMealButton = binding.button
        addMealButton.setOnClickListener {  // increases number of meal inputs by 1
            logCaloriesViewModel.addMealInputViewModel()
            val newIndex = logCaloriesViewModel.numMealInputs.value!! -1
            addNewMealInput(logCaloriesViewModel, newIndex)
        }
        return root
    }

    private fun addFirstMealInput(logCaloriesViewModel: LogCaloriesViewModel) {
        // generates first meal input element (without delete button), adds to layout
        val mealInputsContainer = binding.linearLayout
        val newInputView: View = layoutInflater.inflate(R.layout.meal_input_1, null)
        val newInput: TextInputEditText = newInputView.findViewById(R.id.mealInput)
        newInput.id = 0
        newInput.doAfterTextChanged {  // calculates calorie total on input change
            val strAmount: String = newInput.text.toString()
            var amount = 0
            if (strAmount != "")
                amount = Integer.parseInt(strAmount)
            logCaloriesViewModel.setCalorieI(0, amount)
            logCaloriesViewModel.calculateTotal()
        }
        mealInputsContainer.addView(newInputView)
    }

    private fun addNewMealInput(logCaloriesViewModel: LogCaloriesViewModel, index: Int) {
        // generates new meal input element, adds it to layout
        val mealInputsContainer = binding.linearLayout
        val newInputView: View = layoutInflater.inflate(R.layout.meal_input, null)
        val newInput: TextInputEditText = newInputView.findViewById(R.id.mealInput)
        val deleteButton: Button = newInputView.findViewById(R.id.deleteButton)
        newInput.id = index
        newInput.doAfterTextChanged {  // calculates calorie total on input change
            val strAmount: String = newInput.text.toString()
            var amount = 0
            if (strAmount != "")
                amount = Integer.parseInt(strAmount)
            logCaloriesViewModel.setCalorieI(index, amount)
            logCaloriesViewModel.calculateTotal()
        }
        deleteButton.setOnClickListener {  // delete button
            if (index!=0) {
                logCaloriesViewModel.setCalorieI(index, -1)
                logCaloriesViewModel.calculateTotal()
                mealInputsContainer.removeView(newInputView)
            }
        }
        mealInputsContainer.addView(newInputView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}