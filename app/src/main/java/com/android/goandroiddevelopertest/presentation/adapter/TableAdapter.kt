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
import com.android.goandroiddevelopertest.databinding.TableItemBinding
import com.android.goandroiddevelopertest.databinding.TeamItemBinding
import com.android.goandroiddevelopertest.db.entities.Table

class TableAdapter(private  val context: Context): RecyclerView.Adapter<TableAdapter.TableViewHolder>(){

    inner class TableViewHolder(private val binding: TableItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){

            val standing = differ.currentList[position]

            binding.apply {
                tableTeamPosition.text = standing.position.toString()
                tableTeamName.text = standing.shortName
                firstNo.text = standing.playedGames.toString()
                secondNo.text = standing.won.toString()
                thirdNo.text = standing.points.toString()
                tableTeamImg.load(standing.crest){
                    crossfade(true)
                    error(R.drawable.ic_soccer)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TableViewHolder {
        return TableViewHolder(TableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differList = object: DiffUtil.ItemCallback<Table>(){
        override fun areItemsTheSame(
            oldItem: Table,
            newItem: Table
        ): Boolean {
            return oldItem.teamId == newItem.teamId
        }

        override fun areContentsTheSame(
            oldItem: Table,
            newItem: Table
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differList )

}