package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

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
import edu.ucsb.cs.cs184.caloriecounter.databinding.LogCaloriesFragmentBinding

class LogCaloriesFragment : Fragment() {
    private var _binding: LogCaloriesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this).get(LogCaloriesViewModel::class.java)
        _binding = LogCaloriesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.totalCalories.observe(viewLifecycleOwner) {
            binding.textView3.text = "Daily Total: $it calories"
        }

        val numMealInputsInt: Int? = homeViewModel.numMealInputs.value
        for (i in 0..numMealInputsInt!!) {
            homeViewModel.addMealInputViewModel()
            val newInput = getNewMealInput()
            newInput.id = i
            newInput.doAfterTextChanged {
                recalculateTotal(newInput, homeViewModel)
            }
        }
        val addMealButton = binding.button
        addMealButton.setOnClickListener{  // increases number of meal inputs by 1
            homeViewModel.addMealInputViewModel()
            val newInput = getNewMealInput()
            newInput.id = homeViewModel.numMealInputs.value!!-1
            newInput.doAfterTextChanged {
                recalculateTotal(newInput, homeViewModel)
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