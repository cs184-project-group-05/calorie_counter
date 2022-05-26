package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

        // - - - - - - - - - - Update Function - - - - - - - - - -
        logCaloriesViewModel.update()

        // - - - - - - - - - - Calorie Goal Text - - - - - - - - - -
        val calGoalValue = logCaloriesViewModel.calGoal.value.toString()
        binding.textCalGoal.text =
            "${getString(R.string.calGoal1)} $calGoalValue ${getString(R.string.calGoal2)}"

        // - - - - - - - - - - Daily Total Text - - - - - - - - - -
        logCaloriesViewModel.calCount.observe(viewLifecycleOwner) {
            binding.textDailyCal.text =
                "${getString(R.string.dailyTotal1)} $it ${getString(R.string.dailyTotal2)}"
        }

        // - - - - - - - - - - Draw Meal Inputs from Saved State - - - - - - - - - -
        val numMealInputsInt = logCaloriesViewModel.numMealInputs.value
        if (numMealInputsInt==0) {  // to draw initial meal input
            logCaloriesViewModel.addMealInputViewModel()
        }
        var index = 0
        logCaloriesViewModel.calorieArray.value?.forEach { item ->
            val newInput = getNewMealInput()
            newInput.id = index
            index++
            var inputText = item.toString()
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
        val mealInputsContainer = binding.linearLayout
        val newInputLayout = TextInputLayout(requireActivity(),null,
            com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox)
        newInputLayout.hint = "Enter # of Calories"
        newInputLayout.boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        val newInput = TextInputEditText(newInputLayout.context)
        newInput.inputType = InputType.TYPE_CLASS_NUMBER
        newInput.setSingleLine()
        newInputLayout.addView(newInput)
        mealInputsContainer.addView(newInputLayout)
        return newInput
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}