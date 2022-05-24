package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.ucsb.cs.cs184.caloriecounter.databinding.LogCaloriesFragmentBinding

class LogCaloriesFragment : Fragment() {
    private var _binding: LogCaloriesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(LogCaloriesViewModel::class.java)
        _binding = LogCaloriesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textLog
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        /* adding meal buttons:
            view models stores number of buttons
            add meal input click -> increases number
            numMealInputs -> liveView calls createMealInputs on change
            createMealInputs -> creates text inputs and adds to container
         */

        val addMealButton = binding.button
        addMealButton.setOnClickListener{  // increases number of meal inputs by 1
            homeViewModel.addMealInput()
        }

        homeViewModel.numMealInputs.observe(viewLifecycleOwner) {
            // when numMealInputs changes, draw list of inputs
            // dynamically create text inputs and add to mealButtonsContainer

            val testView: TextView = binding.testNumber
            testView.text = it.toString()


            val mealInputsContainer = binding.linearLayout
//            val mealInputs = mutableListOf<com.google.android.material.textfield.TextInputLayout>()
            val numMealInputsInt: Int = it
            for (i in 1..numMealInputsInt) {
                val newInput = com.google.android.material.textfield.TextInputLayout(requireActivity())
                newInput.hint = "Enter # of Calories"
                newInput.minWidth = 200
                newInput.minimumHeight = 50
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
                newInput.layoutParams = params
//                mealInputs.add(newInput)
                mealInputsContainer.addView(newInput)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}