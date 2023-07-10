package com.sopt.umbba_android.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.umbba_android.data.model.response.ExampleResponseDto
import com.sopt.umbba_android.databinding.ItemQuestionListBinding

class ListQuestionAdapter(private val itemClick: (ExampleResponseDto) -> (Unit)) :
    ListAdapter<ExampleResponseDto, ListQuestionAdapter.ListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemQuestionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class ListViewHolder(
        private val binding: ItemQuestionListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ExampleResponseDto) {
            TODO("서버 연결할 때 채우기")
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ExampleResponseDto>() {
            override fun areItemsTheSame(
                oldItem: ExampleResponseDto,
                newItem: ExampleResponseDto
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ExampleResponseDto,
                newItem: ExampleResponseDto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}