package com.uzuu.learn20_recycler_basic.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzuu.learn20_recycler_basic.databinding.ItemUserBinding
import com.uzuu.learn20_recycler_basic.domain.model.User

class UserAdapter(

    //callback pattern
    // adapter yêu cầu truyền vào 1 hàm có dạng (user) -> Unit
    private val onClick: (User) -> Unit// adapter giữ biến onClick (biến này 9h là lambda truyền từ act)
) : RecyclerView.Adapter<UserAdapter.UserVH>() {

    //adapter giữ dữ liệu
    private val items = mutableListOf<User>()

    fun submit(list: List<User>) {
        items.clear()// xóa
        items.addAll(list)// thêm
        // thông báo đã có sự thay đổi
        notifyDataSetChanged() // bài sau ta thay cái này bằng DiffUtil
    }

    // contructor : thư viện của recycler (nó yêu cầu view)
    //recycler chỉ làm việc với VH
    class UserVH(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserVH(binding)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val user = items[position]
        holder.binding.tvName.text = user.displayName
        holder.binding.tvId.text = "id = ${user.id}"

        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    override fun getItemCount(): Int = items.size
}