package com.uzuu.learn20_2_recycler_dao.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn20_2_recycler_dao.databinding.ItemStudentBinding
import com.uzuu.learn20_2_recycler_dao.domain.model.User

class UserAdapter(
    private val onClick: (User, View) -> Unit
    // kkhi dùng recycler thì mình phải tự quản lý list (dấu *)
): RecyclerView.Adapter<UserAdapter.UserVH>() {

    class UserVH(val binding: ItemStudentBinding): RecyclerView.ViewHolder(binding.root)

    //*
    private val items = mutableListOf<User>()

    //*
    // với cách update kiểu dưới, dù 1 item thay đổi cũng load lại cả list
    fun submit(list: List<User>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserVH(binding)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val user = items[position]

        holder.binding.tvStudentCode.text = "${user.id}"
        holder.binding.tvStudentName.text = "${user.displayName}"

        holder.itemView.setOnClickListener {
            onClick(user, holder.itemView)
        }
    }

    //*
    override fun getItemCount(): Int = items.size

}