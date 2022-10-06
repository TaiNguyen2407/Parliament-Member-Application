package fi.metropolia.projectkotlinoop.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.fragments.PartyListDirections


class PartyListAdapter: ListAdapter<String, PartyListAdapter.PartyListViewHolder>(PartyDiffCallBack) {

    companion object PartyDiffCallBack: DiffUtil.ItemCallback<String>(){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyListViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return PartyListViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: PartyListViewHolder, position: Int) {
        val chosenParty = getItem(position)
        holder.itemView.findViewById<Button>(R.id.button_item).text =
            chosenParty

        //send selected party to PartyMemberList Fragment to display party member of chosen party
        //This is origin destination, data to be sent to receiving destination
        holder.itemView.findViewById<Button>(R.id.button_item).setOnClickListener {
            val action = PartyListDirections.actionPartyListToPartyMemberList(chosenParty)
            holder.itemView.findNavController().navigate(action)
        }

    }

    inner class PartyListViewHolder(view: View): RecyclerView.ViewHolder(view)

}



