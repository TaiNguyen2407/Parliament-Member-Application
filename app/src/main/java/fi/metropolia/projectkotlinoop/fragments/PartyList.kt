package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import fi.metropolia.projectkotlinoop.context.MemberApplication
import fi.metropolia.projectkotlinoop.viewmodel.PartyListViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyListViewModelFactory
import fi.metropolia.projectkotlinoop.adapter.PartyListAdapter
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyListBinding


/**
 * A simple PartyList Fragment.
 */
class PartyList : Fragment() {
    private var binding: FragmentPartyListBinding? = null
    //Initialize View Model
    private val partyListViewModel: PartyListViewModel by activityViewModels {
        PartyListViewModelFactory(
            (activity?.application as MemberApplication).database.memberDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentPartyListBinding = FragmentPartyListBinding
            .inflate(inflater, container, false)
        binding = fragmentPartyListBinding
        return fragmentPartyListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PartyListAdapter()

        //Set Layout Manager of Recycler view
        binding?.partyListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        //Set adapter for recycler view
        binding?.partyListRecyclerView?.adapter = adapter

        //Code for observe pattern
        partyListViewModel.partyDisplayed.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}