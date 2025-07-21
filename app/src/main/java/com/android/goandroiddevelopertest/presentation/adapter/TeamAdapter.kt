package com.android.goandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.OnTeamItemClickListener
import com.android.goandroiddevelopertest.R
import com.android.goandroiddevelopertest.databinding.CompetitionItemBinding
import com.android.goandroiddevelopertest.databinding.MatchItemBinding
import com.android.goandroiddevelopertest.databinding.TeamItemBinding
import com.android.goandroiddevelopertest.db.entities.Team

class TeamAdapter(private  val context: Context, private val onTeamItemClickListener: OnTeamItemClickListener): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(){

    inner class TeamViewHolder(private val binding: TeamItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            val team = differ.currentList[position]
            binding.competitionTeamName.text = team.shortName
            binding.competitionTeamImg.load(team.crest){
                crossfade(true)
            }
            binding.teamCard.setOnClickListener {
                onTeamItemClickListener.onTeamClick(team)
            }
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

    private val differList = object: DiffUtil.ItemCallback<Team>(){
        override fun areItemsTheSame(
            oldItem: Team,
            newItem: Team
        ): Boolean {
            return oldItem.teamId == newItem.teamId
        }

        override fun areContentsTheSame(
            oldItem: Team,
            newItem: Team
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differList )

}