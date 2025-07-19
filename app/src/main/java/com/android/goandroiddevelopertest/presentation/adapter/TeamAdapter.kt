package com.android.goandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.OnTeamItemClickListener
import com.android.goandroiddevelopertest.databinding.CompetitionItemBinding
import com.android.goandroiddevelopertest.databinding.MatchItemBinding
import com.android.goandroiddevelopertest.databinding.TeamItemBinding

class TeamAdapter(private  val context: Context, private val onTeamItemClickListener: OnTeamItemClickListener): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){

    inner class TeamViewHolder(private val binding: TeamItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamViewHolder {
        return TeamViewHolder(TeamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
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