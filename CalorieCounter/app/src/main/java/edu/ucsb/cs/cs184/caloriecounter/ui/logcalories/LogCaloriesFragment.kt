package edu.ucsb.cs.cs184.caloriecounter.ui.logcalories

import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.ucsb.cs.cs184.caloriecounter.R
import edu.ucsb.cs.cs184.caloriecounter.databinding.LogCaloriesFragmentBinding

class LogCaloriesFragment : Fragment() {

    private var _binding: LogCaloriesFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val logCaloriesViewModel =
            ViewModelProvider(this).get(LogCaloriesViewModel::class.java)

        _binding = LogCaloriesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // - - - - - - - - - - Update Function - - - - - - - - - -
        logCaloriesViewModel.update()

        // - - - - - - - - - - Calorie Goal Text - - - - - - - - - -
        val calGoal: TextView = binding.textCalGoal
        calGoal.text = getString(R.string.calGoal1).plus(logCaloriesViewModel.calGoal.value.toString()).plus(getString(R.string.calGoal2))

        // - - - - - - - - - - Daily Total Text - - - - - - - - - -
        val calCount: TextView = binding.textDailyCal
        calCount.text = getString(R.string.dailyTotal1).plus(logCaloriesViewModel.calCount.value.toString()).plus(getString(R.string.dailyTotal2))
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}