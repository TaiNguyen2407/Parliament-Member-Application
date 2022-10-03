package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import fi.metropolia.projectkotlinoop.MemberApplication
import fi.metropolia.projectkotlinoop.PartyListViewModel
import fi.metropolia.projectkotlinoop.PartyListViewModelFactory
import fi.metropolia.projectkotlinoop.adapter.PartyListAdapter
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.data.Parliarment
//import fi.metropolia.projectkotlinoop.PartyListViewModelFactory
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyListBinding
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [PartyList.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyList : Fragment() {
    // TODO: Rename and change types of parameters
    private var binding: FragmentPartyListBinding? = null
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
        val fragmentPartyListBinding = FragmentPartyListBinding.inflate(inflater, container, false)
        binding = fragmentPartyListBinding
        return fragmentPartyListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = PartyListAdapter()

        binding?.partyListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
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