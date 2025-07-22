package com.android.goandroiddevelopertest.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.goandroiddevelopertest.Constants.fromIsoToString
import com.android.goandroiddevelopertest.data.model.MatchModel
import com.android.goandroiddevelopertest.databinding.MatchItemBinding
import com.android.goandroiddevelopertest.db.entities.Match

class FixturesAdapter(private  val context: Context): RecyclerView.Adapter<FixturesAdapter.MatchViewHolder>(){

    inner class MatchViewHolder(private val binding: MatchItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            val match = differ.currentList[position]

            binding.apply {
                matchHomeTeamTv.text = match.homeTeam.shortName
                matchHomeScoreTv.text = match.score.fullTime.home.toString()
                matchAwayTeamTv.text = match.awayTeam.shortName
                matchAwayScoreTv.text = match.score.fullTime.away.toString()
                timeThirdTv.text = "MD ${match.matchday}"
                matchTimeTv.text = match.utcDate.fromIsoToString(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    "HH:mm"
                )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchViewHolder {
        return MatchViewHolder(MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differList = object: DiffUtil.ItemCallback<MatchModel>(){
        override fun areItemsTheSame(
            oldItem: MatchModel,
            newItem: MatchModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MatchModel,
            newItem: MatchModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differList )

}