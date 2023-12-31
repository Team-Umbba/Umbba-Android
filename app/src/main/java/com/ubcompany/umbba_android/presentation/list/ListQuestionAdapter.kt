package com.ubcompany.umbba_android.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ubcompany.umbba_android.data.model.response.ListResponseDto
import com.ubcompany.umbba_android.databinding.ItemQuestionListBinding
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import timber.log.Timber

class ListQuestionAdapter(private val itemClick: (ListResponseDto.ListData) -> (Unit)) :
    ListAdapter<ListResponseDto.ListData, ListQuestionAdapter.ListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemQuestionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class ListViewHolder(
        private val binding: ItemQuestionListBinding,
        private val itemClick: (ListResponseDto.ListData) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ListResponseDto.ListData) {
            with(binding) {
                tvIndex.text = data.index.toString()
                tvTopic.text = data.topic
                root.setOnSingleClickListener {
                    itemClick(data)
                    Timber.d("아이템 클릭 이벤트 발생")
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ListResponseDto.ListData>() {
            override fun areItemsTheSame(
                oldItem: ListResponseDto.ListData,
                newItem: ListResponseDto.ListData
            ): Boolean {
                return oldItem.qnaId == newItem.qnaId
            }

            override fun areContentsTheSame(
                oldItem: ListResponseDto.ListData,
                newItem: ListResponseDto.ListData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}