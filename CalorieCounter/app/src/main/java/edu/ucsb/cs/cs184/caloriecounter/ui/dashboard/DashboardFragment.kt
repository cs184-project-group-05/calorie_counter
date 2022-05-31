package edu.ucsb.cs.cs184.caloriecounter.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.ucsb.cs.cs184.caloriecounter.R
import edu.ucsb.cs.cs184.caloriecounter.databinding.FragmentDashboardBinding
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.extension.getColorInt

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun getIndicators(indicatorInformation: List<Pair<CalendarDate, Boolean>>): List<DashboardViewModel.Indicator> {
        val ret: MutableList<DashboardViewModel.Indicator> = mutableListOf()
        for (pair in indicatorInformation) {
            val date = pair.first
            val color = if (pair.second) { requireContext().getColorInt(R.color.green) } else { requireContext().getColorInt(R.color.red) }
            ret.add(DashboardViewModel.Indicator(date, color))
        }
        return ret
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val calendarView = binding.calendarView
        val initialDate = dashboardViewModel.getInitialDate()
        val selectionMode = dashboardViewModel.getSelectionMode()
        val firstDayOfWeek = dashboardViewModel.getFirstDayOfWeek()
        val showYearSelectionView = dashboardViewModel.getShowYearSelectionView()
        calendarView.setupCalendar(
            initialDate = initialDate,
            selectionMode = selectionMode,
            firstDayOfWeek = firstDayOfWeek,
            showYearSelectionView = showYearSelectionView
        )
        calendarView.datesIndicators = this.getIndicators(dashboardViewModel.getIndicatorInformation())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}