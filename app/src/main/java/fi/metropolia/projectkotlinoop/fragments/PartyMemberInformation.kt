package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import fi.metropolia.projectkotlinoop.MemberApplication
import fi.metropolia.projectkotlinoop.data.ParliamentMember
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberInformationBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModelFactory
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [PartyMemberInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMemberInformation : Fragment() {
    private var binding: FragmentPartyMemberInformationBinding? = null
    private lateinit var chosenMember: String
    private val safeArgs: PartyMemberInformationArgs by navArgs()
    private val partyMemberInformationViewModel: PartyMemberInformationViewModel by activityViewModels {
        PartyMemberInformationViewModelFactory(
            (activity?.application as MemberApplication).database.memberDao()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            chosenMember = safeArgs.chosenMember.firstname.toString()
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentPartyMemberInformationBinding = FragmentPartyMemberInformationBinding.inflate(inflater, container, false)
        binding = fragmentPartyMemberInformationBinding

        return fragmentPartyMemberInformationBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isMemberAMinister = safeArgs.chosenMember.minister.toString()
        val memberFullName = safeArgs.chosenMember.firstname.uppercase()+" "+ safeArgs.chosenMember.lastname.uppercase()
        val memberParty = safeArgs.chosenMember.party.uppercase()
        val memberHetekaId = safeArgs.chosenMember.hetekaId.toString()
        val memberSeatNumber = safeArgs.chosenMember.seatNumber.toString()

        binding?.hetekaid?.text = "Heteka Id: " + memberHetekaId
        binding?.party?.text = "Party: " + memberParty
        binding?.seatNumber?.text = "Seat Number: " + memberSeatNumber
        if((isMemberAMinister) == "true"){
            binding?.fullName?.text = "Minister " + memberFullName
        } else {
            binding?.fullName?.text = memberFullName
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}