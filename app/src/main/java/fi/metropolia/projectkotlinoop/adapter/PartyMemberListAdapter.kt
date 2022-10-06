package fi.metropolia.projectkotlinoop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.fragments.PartyMemberListDirections


class PartyMemberListAdapter: ListAdapter<ParliamentMember, PartyMemberListAdapter.PartyMemberListViewHolder>(
    PartyMemberDiffCallBack
) {

    inner class PartyMemberListViewHolder(view: View): RecyclerView.ViewHolder(view)

    companion object PartyMemberDiffCallBack: DiffUtil.ItemCallback<ParliamentMember>(){

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
        val chosenMember = getItem(position).firstname +" "+ getItem(position).lastname

        holder.itemView.findViewById<Button>(R.id.button_item).text = chosenMember

        val parliamentMember = getItem(position)

        holder.itemView.findViewById<Button>(R.id.button_item).setOnClickListener {
            val action = PartyMemberListDirections.actionPartyMemberListToPartyMemberInformation(parliamentMember)
            holder.itemView.findNavController().navigate(action)
        }
    }

}





