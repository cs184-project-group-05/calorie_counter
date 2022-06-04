package edu.ucsb.cs.cs184.caloriecounter.ui.leaderboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recylerView = binding.recyclerview
        recylerView.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<StreakItemViewModel>()

        // Dummy data for now
        for (i in 1..20) {
            data.add(StreakItemViewModel("John Smith", "1"))
        }

        val adapter = StreakItemAdapter(data)
        recylerView.adapter = adapter

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LeaderboardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}