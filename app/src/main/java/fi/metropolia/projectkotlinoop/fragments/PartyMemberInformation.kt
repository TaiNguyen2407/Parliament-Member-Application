package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import fi.metropolia.projectkotlinoop.MemberApplication
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberInformationBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PartyMemberInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMemberInformation : Fragment() {
    // TODO: Rename and change types of parameters
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
        arguments?.let {
            chosenMember = safeArgs.chosenMember
        }


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
        binding?.hetekaid?.text = partyMemberInformationViewModel.hetekaIdDisplayed.toString()
        binding?.party?.text = partyMemberInformationViewModel.partyDisplayed.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}