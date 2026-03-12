package com.uzuu.learn19_3_practice_navigation.feature.task.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.uzuu.learn19_3_practice_navigation.databinding.FragmentTaskDetailBinding
import com.uzuu.learn19_3_practice_navigation.feature.main.MainActivity
import kotlinx.coroutines.launch

class TaskDetailFragment : Fragment() {

    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!

    private val args: TaskDetailFragmentArgs by navArgs()

    private val vm: TaskDetailViewModel by viewModels {
        (requireActivity() as MainActivity).detailFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val taskId = args.taskId

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.observeTask(taskId).collect { task ->
                        if (task == null) {
                            binding.tvTitle.text = "Task not found"
                            binding.tvDone.text = ""
                        } else {
                            binding.tvTitle.text = "${task.id}. ${task.title}"
                            binding.tvDone.text = if (task.done) "Done ✅" else "Not done ❌"
                        }
                    }
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            val action = TaskDetailFragmentDirections
                .actionTaskDetailFragmentToTaskEditFragment(taskId)
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}