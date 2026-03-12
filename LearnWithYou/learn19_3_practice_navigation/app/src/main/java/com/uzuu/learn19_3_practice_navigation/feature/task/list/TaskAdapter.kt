package com.uzuu.learn19_3_practice_navigation.feature.task.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn19_3_practice_navigation.databinding.ItemTaskBinding
import com.uzuu.learn19_3_practice_navigation.domain.model.Task

class TaskAdapter(
    private val onClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.VH>() {

    private val items = mutableListOf<Task>()

    fun submit(list: List<Task>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class VH(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = items[position]
        holder.binding.tvTitle.text = "${task.id}. ${task.title}"
        holder.binding.tvDone.text = if (task.done) "Done ✅" else "Not done ❌"
        holder.binding.root.setOnClickListener { onClick(task) }
    }
}