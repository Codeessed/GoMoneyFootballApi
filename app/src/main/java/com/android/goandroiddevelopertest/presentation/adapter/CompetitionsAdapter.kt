package com.android.goandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.data.model.CompetitionModel
import com.android.goandroiddevelopertest.data.model.CompetitionResponseModel
import com.android.goandroiddevelopertest.databinding.CompetitionItemBinding
import com.android.goandroiddevelopertest.databinding.MatchItemBinding

class CompetitionsAdapter(private  val context: Context, private val onCompetitionItemClickListener: OnCompetitionItemClickListener): RecyclerView.Adapter<CompetitionsAdapter.CompetitionViewHolder>(){

    inner class CompetitionViewHolder(private val binding: CompetitionItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            val competition = differ.currentList[position]

            binding.apply {
                competitionTitle.text = competition.name
                competitionItem.setOnClickListener{
                    onCompetitionItemClickListener.onCompetitionClick(position)
                }
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

    private val differList = object: DiffUtil.ItemCallback<CompetitionModel>(){
        override fun areItemsTheSame(
            oldItem: CompetitionModel,
            newItem: CompetitionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CompetitionModel,
            newItem: CompetitionModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differList )

}