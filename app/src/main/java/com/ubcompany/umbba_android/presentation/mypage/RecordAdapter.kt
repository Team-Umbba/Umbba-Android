package com.ubcompany.umbba_android.presentation.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ubcompany.umbba_android.data.model.response.RecordListResponseDto
import com.ubcompany.umbba_android.databinding.ItemRecordListBinding
import com.ubcompany.umbba_android.util.setOnSingleClickListener

class RecordAdapter(private val itemClick: (RecordListResponseDto.RecordListData) -> (Unit)) :
    ListAdapter<RecordListResponseDto.RecordListData, RecordAdapter.RecordViewHolder>(diffUtil) {

    private var onRootClickListener: OnRootClickListener? = null

    interface OnRootClickListener {
        fun touchRecordItem(isTouched: Boolean, itemBinding: ItemRecordListBinding)
    }

    fun initListener(pOnClick: OnRootClickListener) {
        this.onRootClickListener = pOnClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding =
            ItemRecordListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class RecordViewHolder(
        private val binding: ItemRecordListBinding,
        private val itemClick: (RecordListResponseDto.RecordListData) -> (Unit)
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RecordListResponseDto.RecordListData) {
            with(binding) {
                Log.d("yeonjin adapter", "image: ${data.imgUrl}")
                ivRecord.load(data.imgUrl)
                tvTitle.text = data.title
                tvPictureDescription.text = data.content
                tvWriter.text = data.writer

                btnDelete.setOnSingleClickListener {
                    itemClick(data)
                }

                root.setOnSingleClickListener {
                    onRootClickListener?.touchRecordItem(true, binding)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecordListResponseDto.RecordListData>() {
            override fun areItemsTheSame(
                oldItem: RecordListResponseDto.RecordListData,
                newItem: RecordListResponseDto.RecordListData
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecordListResponseDto.RecordListData,
                newItem: RecordListResponseDto.RecordListData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}