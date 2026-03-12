package com.uzuu.learn20_1_recycler_crud_basic.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn20_1_recycler_crud_basic.databinding.ItemStudentBinding
import com.uzuu.learn20_1_recycler_crud_basic.domain.model.User

class UserAdapter(

    //callback pattern
    // adapter yêu cầu truyền vào 1 hàm có dạng (user) -> Unit
    private val onClick: (User) -> Unit
// adapter giữ biến onClick (biến này 9h là lambda truyền từ act)
) : RecyclerView.Adapter<UserAdapter.UserVH>() {
    class UserVH(val binding: ItemStudentBinding) : RecyclerView.ViewHolder(binding.root)

    private val items = mutableListOf<User>()

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
        holder.binding.tvStudentCode.text = "id = ${user.id}"
        holder.binding.tvStudentName.text = user.displayName

        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

//    override fun getItemCount(): Int {
//        return items.size
//    }
    override fun getItemCount () : Int = items.size
}