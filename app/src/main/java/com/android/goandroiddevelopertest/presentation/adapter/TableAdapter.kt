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
import com.android.goandroiddevelopertest.databinding.TableItemBinding
import com.android.goandroiddevelopertest.databinding.TeamItemBinding

class TableAdapter(private  val context: Context): RecyclerView.Adapter<TableAdapter.TableViewHolder>(){

    inner class TableViewHolder(private val binding: TableItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
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