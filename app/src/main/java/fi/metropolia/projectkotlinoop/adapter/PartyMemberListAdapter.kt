package fi.metropolia.projectkotlinoop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.data.ParliamentMember


class PartyMemberListAdapter(): ListAdapter<ParliamentMember, PartyMemberListAdapter.PartyMemberListViewHolder>(
    partyMemberDiffCallBack
) {

    inner class PartyMemberListViewHolder(view: View): RecyclerView.ViewHolder(view)

    companion object partyMemberDiffCallBack: DiffUtil.ItemCallback<ParliamentMember>(){

        override fun areItemsTheSame(
            oldItem: ParliamentMember,
            newItem: ParliamentMember
        ): Boolean {
            return oldItem.hetekaId == newItem.hetekaId
        }

        override fun areContentsTheSame(
            oldItem: ParliamentMember,
            newItem: ParliamentMember
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyMemberListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return PartyMemberListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PartyMemberListViewHolder, position: Int) {
        holder.itemView.findViewById<Button>(R.id.button_item).text = getItem(position).firstname +" "+ getItem(position).lastname
    }

}





