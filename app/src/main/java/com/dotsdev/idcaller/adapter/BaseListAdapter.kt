package com.dotsdev.idcaller.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
abstract class BaseListAdapter<T : Identifiable, Binding : ViewDataBinding> :
    ListAdapter<T, BaseListAdapter.GenericViewHolder>(ItemCallbackUtil.create()) {
    class GenericViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    abstract fun createBinding(parent: ViewGroup): Binding
    abstract fun onBind(binding: Binding, data: T, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        return GenericViewHolder(createBinding(parent))
    }

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        onBind(holder.binding as Binding, getItem(position), position)
    }
}

interface Identifiable {
    val id: String
    override fun equals(other: Any?): Boolean
}

object ItemCallbackUtil {
    fun <T : Identifiable> create(): DiffUtil.ItemCallback<T> {
        return object : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }
        }
    }
}