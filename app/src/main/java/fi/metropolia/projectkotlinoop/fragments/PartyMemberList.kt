package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import fi.metropolia.projectkotlinoop.context.MemberApplication
import fi.metropolia.projectkotlinoop.adapter.PartyMemberListAdapter
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberListBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberListViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberListViewModelFactory

/**
 * A simple List of Party Member Fragment
 * This Fragment is receiving destination of argument sent from origin destination (PartyList Fragment)
 */
class PartyMemberList : Fragment() {
    private var binding: FragmentPartyMemberListBinding? = null
    //Declare late init var for argument sent from PartyList Fragment (origin destination)
    private lateinit var chosenParty: String
    //Safe Args used to received argument sent from origin destination
    private val safeArgs: PartyMemberListArgs by navArgs()
    //Initialize View Model
    private val partyMemberListViewModel: PartyMemberListViewModel by activityViewModels {
        PartyMemberListViewModelFactory(
            (activity?.application as MemberApplication).database.memberDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //receiving destination for safeArgs
        arguments.let {
            chosenParty = safeArgs.chosenParty
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentPartyListMemberBinding = FragmentPartyMemberListBinding.inflate(inflater,container,false)
        binding = fragmentPartyListMemberBinding
        return fragmentPartyListMemberBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PartyMemberListAdapter()

        //Set layout Manager for recycler view
        binding?.partyMemberListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        //Set adapter for recycler view
        binding?.partyMemberListRecyclerView?.adapter = adapter

        //Receive chosen party from user sent from PartyList Fragment
        //in order to display member of selected party
        partyMemberListViewModel.chosenParty(chosenParty)

        //Code for observe pattern
        partyMemberListViewModel.memberDisplayed
            .observe(viewLifecycleOwner){
                adapter.submitList(it)
            }

    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}