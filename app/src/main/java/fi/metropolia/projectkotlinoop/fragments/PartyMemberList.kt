package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import fi.metropolia.projectkotlinoop.MemberApplication
import fi.metropolia.projectkotlinoop.adapter.PartyMemberListAdapter
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.data.Parliarment
import fi.metropolia.projectkotlinoop.data.Parliarment.ParliamentMembersData.members
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberListBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberListViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberListViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [PartyMemberList.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMemberList : Fragment() {
    private var binding: FragmentPartyMemberListBinding? = null
    private lateinit var chosenParty: String
    private val safeArgs: PartyMemberListArgs by navArgs()
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
    ): View? {
        // Inflate the layout for this fragment
        val fragmentPartyListMemberBinding = FragmentPartyMemberListBinding.inflate(inflater,container,false)
        binding = fragmentPartyListMemberBinding
        return fragmentPartyListMemberBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = PartyMemberListAdapter()

        binding?.partyMemberListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.partyMemberListRecyclerView?.adapter = adapter

        partyMemberListViewModel.memberDisplayed.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }





    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}