package com.android.goandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.goandroiddevelopertest.Constants.fromIsoToString
import com.android.goandroiddevelopertest.OnCompetitionItemClickListener
import com.android.goandroiddevelopertest.OnTeamItemClickListener
import com.android.goandroiddevelopertest.R
import com.android.goandroiddevelopertest.databinding.CompetitionItemBinding
import com.android.goandroiddevelopertest.databinding.MatchItemBinding
import com.android.goandroiddevelopertest.databinding.SquadItemBinding
import com.android.goandroiddevelopertest.databinding.TableItemBinding
import com.android.goandroiddevelopertest.databinding.TeamItemBinding
import com.android.goandroiddevelopertest.db.entities.Squad
import com.android.goandroiddevelopertest.db.entities.Table

class SquadAdapter(private  val context: Context): RecyclerView.Adapter<SquadAdapter.SquadViewHolder>(){

    inner class SquadViewHolder(private val binding: SquadItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){

            val squad = differ.currentList[position]

            binding.apply {

                squadIndex.text = position.toString()
                squadPosition.text = squad.position
                squadName.text = squad.name

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SquadViewHolder {
        return SquadViewHolder(SquadItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differList = object: DiffUtil.ItemCallback<Squad>(){
        override fun areItemsTheSame(
            oldItem: Squad,
            newItem: Squad
        ): Boolean {
            return oldItem.squadId == newItem.squadId
        }

        override fun areContentsTheSame(
            oldItem: Squad,
            newItem: Squad
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differList )

}