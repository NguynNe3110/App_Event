package com.uzuu.learn19_3_practice_navigation.feature.task.edit

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
import com.uzuu.learn19_3_practice_navigation.R
import com.uzuu.learn19_3_practice_navigation.feature.task.edit.TaskEditFragmentArgs
import com.uzuu.learn19_3_practice_navigation.databinding.FragmentTaskEditBinding
import com.uzuu.learn19_3_practice_navigation.domain.model.Task
import com.uzuu.learn19_3_practice_navigation.feature.main.MainActivity
import kotlinx.coroutines.launch

class TaskEditFragment : Fragment() {

    private var _binding: FragmentTaskEditBinding? = null
    private val binding get() = _binding!!

    private val args: TaskEditFragmentArgs by navArgs()

    private val vm: TaskEditViewModel by viewModels {
        (requireActivity() as MainActivity).editFactory
    }

    private var currentTask: Task? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val taskId = args.taskId

        // Observe task from Room
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.observeTask(taskId).collect { task ->
                        currentTask = task
                        if (task == null) return@collect

                        // set UI (tránh loop text change)
                        if (binding.edtTitle.text.toString() != task.title) {
                            binding.edtTitle.setText(task.title)
                            binding.edtTitle.setSelection(task.title.length)
                        }
                        binding.cbDone.isChecked = task.done
                    }
                }
            }
        }

        // Save
        binding.btnSave.setOnClickListener {
            val t = currentTask ?: return@setOnClickListener
            val newTitle = binding.edtTitle.text.toString().trim()
            val newDone = binding.cbDone.isChecked

            vm.save(t.copy(title = newTitle, done = newDone))

            // quay lại Detail (hoặc popBackStack 1 lần cũng được)
            findNavController().popBackStack()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSaveAndQuit.setOnClickListener {
            val t = currentTask ?: return@setOnClickListener
            val newTitle = binding.edtTitle.text.toString().trim()
            val newDone = binding.cbDone.isChecked

            vm.save(t.copy(title = newTitle, done = newDone))

            // quay lại Detail (hoặc popBackStack 1 lần cũng được)
            findNavController().popBackStack(R.id.taskListFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}