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

class Leaderboard : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LeaderboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LeaderboardViewModel::class.java]
        val root: View = binding.root

        // display leaderboard live data
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.getCurStreaksMutableLiveData().observe(viewLifecycleOwner) {
            val adapter = StreakItemAdapter(viewModel.getSortedData())
            recyclerView.adapter = adapter
        }

        return root
    }
}