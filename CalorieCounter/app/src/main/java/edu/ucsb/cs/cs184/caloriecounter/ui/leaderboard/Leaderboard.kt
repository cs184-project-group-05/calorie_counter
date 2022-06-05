package edu.ucsb.cs.cs184.caloriecounter.ui.leaderboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ucsb.cs.cs184.caloriecounter.databinding.FragmentLeaderboardBinding
import edu.ucsb.cs.cs184.caloriecounter.ui.logcalories.LogCaloriesViewModel

class Leaderboard : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LeaderboardViewModel
    private var data = ArrayList<StreakItemViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LeaderboardViewModel::class.java]
        val root: View = binding.root
        val recylerView = binding.recyclerview
        recylerView.layoutManager = LinearLayoutManager(context)
        viewModel.updateStreaks()
        viewModel.getCurStreaksMutableLiveData().observe(viewLifecycleOwner){
            data = ArrayList<StreakItemViewModel>()

            viewModel.getCurStreaksMutableLiveData().value?.forEachIndexed { index, userStreak ->
                data.add(StreakItemViewModel(userStreak.name.toString(), userStreak.streak.toString()))
            }
            var sortedData = data.sortedByDescending { item -> item.streak.toInt() }
            val adapter = StreakItemAdapter(sortedData)
            recylerView.adapter = adapter
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaderboardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}