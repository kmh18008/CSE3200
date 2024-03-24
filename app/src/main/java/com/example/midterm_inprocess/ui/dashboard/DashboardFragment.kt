package com.example.midterm_inprocess.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.midterm_inprocess.databinding.FragmentDashboardBinding
import com.example.midterm_inprocess.databinding.FragmentHomeBinding
import com.example.midterm_inprocess.ui.home.HomeViewModel

class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        viewModel.time.observe(viewLifecycleOwner) { formattedTime ->
            binding.timerText.text = formattedTime
        }

        binding.startButton.setOnClickListener { viewModel.startTimer() }
        binding.stopPauseButton.setOnClickListener { viewModel.stopPauseTimer() }
        binding.resetButton.setOnClickListener { viewModel.resetTimer() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}