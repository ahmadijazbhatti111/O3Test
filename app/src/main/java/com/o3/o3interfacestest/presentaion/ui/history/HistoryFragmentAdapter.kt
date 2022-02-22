package com.o3.o3interfacestest.presentaion.ui.history

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.o3.o3interfacestest.R
import com.o3.o3interfacestest.databinding.HistoryListItemBinding
import com.o3.o3interfacestest.domain.model.User


class HistoryFragmentAdapter : ListAdapter<User, HistoryFragmentAdapter.ViewHolder>(
        ResultItemDiffCallback()
    ) {
var delUserCallback:((userData:User)->Unit)?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.history_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.btndel.setOnClickListener {
            Log.e("TAG", "onBindViewHolder: "+currentList.size+"    $position == ${holder.adapterPosition}" )
            delUserCallback?.invoke(getItem(holder.adapterPosition))
        }
    }

    class ViewHolder(
         val binding: HistoryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            with(binding) {
                binding.model = user
                executePendingBindings()
            }
        }
    }
}

class ResultItemDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem.name == newItem.name
    }
}


