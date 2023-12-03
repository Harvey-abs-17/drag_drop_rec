package com.example.draganddrop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.draganddrop.databinding.ItemNameBinding
import com.example.draganddrop.databinding.ItemNameWithAgeBinding
import java.lang.IllegalArgumentException


const val ITEM_NAME = 0
const val ITEM_NAME_WITH_AGE = 1

class PersonsAdapter :RecyclerView.Adapter< RecyclerView.ViewHolder >() {


    inner class ItemNameViewHolder(private val binding: ItemNameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindViews(item: PersonData) {

            binding.apply {

                nameTextView.text = item.name

            }

        }
    }

    inner class ItemNameWithAgeViewHolder(private val binding: ItemNameWithAgeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindViews(item: PersonData) {

            binding.apply {

                nameTextView.text = item.name
                ageTextView.text = item.age.toString()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            ITEM_NAME ->{
                val binding = ItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemNameViewHolder(binding)
            }
            ITEM_NAME_WITH_AGE ->{
                val binding = ItemNameWithAgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemNameWithAgeViewHolder(binding)
            }

            else -> { throw IllegalArgumentException("error") }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            ITEM_NAME -> (holder as ItemNameViewHolder).bindViews(differ.currentList[position])
            ITEM_NAME_WITH_AGE -> (holder as ItemNameWithAgeViewHolder).bindViews(differ.currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if( differ.currentList[position] == null ){
            ITEM_NAME
        }else{
            ITEM_NAME_WITH_AGE
        }
    }

    private val differCallback = object :DiffUtil.ItemCallback<PersonData>(){
        override fun areItemsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PersonData, newItem: PersonData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


}