package com.example.midterm_inprocess.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.midterm_inprocess.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(requireActivity()).get(NotificationsViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

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
