package com.android.goandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.databinding.CompetitionItemBinding
import com.android.goandroiddevelopertest.databinding.MatchItemBinding

class CompetitionsAdapter(private  val context: Context, private val onCompetitionItemClickListener: OnCompetitionItemClickListener): RecyclerView.Adapter<CompetitionsAdapter.CompetitionViewHolder>(){

    inner class CompetitionViewHolder(private val binding: CompetitionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.competitionItem.setOnClickListener{
                onCompetitionItemClickListener.onCompetitionClick(position)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompetitionViewHolder {
        return CompetitionViewHolder(CompetitionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CompetitionViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differList = object: DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differList )

}