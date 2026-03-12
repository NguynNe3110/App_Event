package com.uzuu.learn19_3_practice_navigation.feature.task.list

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.uzuu.learn19_3_practice_navigation.feature.task.list.TaskListFragmentDirections
import com.uzuu.learn19_3_practice_navigation.databinding.FragmentTaskListBinding
import com.uzuu.learn19_3_practice_navigation.feature.main.MainActivity
import kotlinx.coroutines.launch

class TaskListFragment : Fragment() {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val vm: TaskListViewModel by viewModels {
        (requireActivity() as MainActivity).listFactory
    }

    private lateinit var adapter: TaskAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TaskAdapter { task ->
            val action = TaskListFragmentDirections
                .actionTaskListFragmentToTaskDetailFragment(task.id)
            findNavController().navigate(action)
        }

        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.tasks.collect { list ->
                        adapter.submit(list)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}