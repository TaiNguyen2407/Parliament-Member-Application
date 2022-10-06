package fi.metropolia.projectkotlinoop.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fi.metropolia.projectkotlinoop.MemberApplication
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.data.MemberLikes
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberInformationBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModelFactory
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberListViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberListViewModelFactory
import java.lang.reflect.Member
import java.util.*
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * A simple [Fragment] subclass.
 * Use the [PartyMemberInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMemberInformation : Fragment() {
    private var binding: FragmentPartyMemberInformationBinding? = null
    private val safeArgs: PartyMemberInformationArgs by navArgs()
    val partyMemberInformationViewModel: PartyMemberInformationViewModel by viewModels {
        PartyMemberInformationViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout for fragment
        val fragmentPartyMemberInformationBinding = FragmentPartyMemberInformationBinding.inflate(inflater, container, false)
        binding = fragmentPartyMemberInformationBinding
        return fragmentPartyMemberInformationBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isMemberAMinister = safeArgs.chosenMember.minister.toString()
        val memberFullName = safeArgs.chosenMember.firstname.uppercase() + " " + safeArgs.chosenMember.lastname.uppercase()
        val memberParty = safeArgs.chosenMember.party.uppercase()
        val memberHetekaId = safeArgs.chosenMember.hetekaId.toString()
        val memberSeatNumber = safeArgs.chosenMember.seatNumber.toString()
        val memberPicture = safeArgs.chosenMember.pictureUrl

        //insert/load selected member's picture URL using Glide
        binding?.let {
            Glide.with(this).load("https://avoindata.eduskunta.fi/$memberPicture")
                .apply(RequestOptions.centerInsideTransform())
                .into(it.imageView)
        }
        //Set selected member's name
        binding?.hetekaid?.text = "Heteka Id: $memberHetekaId"

        //Set selected member's party
        binding?.party?.text = "Party: $memberParty"

        //Set selected member's seat number
        binding?.seatNumber?.text = "Seat Number: $memberSeatNumber"

        //Check if selected member is minister or not, if yes add Minister in front of name
        if ((isMemberAMinister) == "true") {
            binding?.fullName?.text = "Minister $memberFullName"
        } else {
            binding?.fullName?.text = memberFullName
        }



        binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)

        binding?.likeButton?.setOnClickListener {
            //Toast.makeText(requireContext(), "You liked member: $memberFullName", Toast.LENGTH_SHORT).show()
            partyMemberInformationViewModel.insertLikesData(MemberLikes(memberHetekaId.toInt(), 0, 0, 1))
        }


        binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)

        binding?.dislikeButton?.setOnClickListener {
            //Toast.makeText(requireContext(), "You disliked member: ${memberFullName}", Toast.LENGTH_SHORT).show()
            partyMemberInformationViewModel
                .insertLikesData(MemberLikes(memberHetekaId.toInt(), 0, 0, -1))
        }

        partyMemberInformationViewModel.setHetekaId(memberHetekaId.toInt())

        partyMemberInformationViewModel.likesNum.observe(viewLifecycleOwner){
            binding?.likes?.text = it.toString()
        }
        partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner){
            binding?.dislikes?.text = it.toString()
        }


    }

    /*private fun updateLikeStatus() {
        if (isDislikeSelected == true) {
            binding?.likeButton?.isEnabled = false
            binding?.likeButton?.isClickable = false
        } else{
            binding?.likeButton?.setOnClickListener {
                if (!isLikeSelected) {
                    isLikeSelected = true
                    binding?.likeButton?.setImageResource(R.drawable.thumbs_up_selected)
                    binding?.likes?.setTextColor(Color.BLUE)
                    binding?.likes?.text = numLikes.toInt().inc().toString()
                } else {
                    isLikeSelected = false
                    binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)
                    binding?.likes?.setTextColor(Color.GRAY)
                    binding?.likes?.text = numLikes
                }
            }
        }
    }
    private fun updateDislikeStatus() {
        if (isLikeSelected == true) {
            binding?.dislikeButton?.isEnabled = false
            binding?.dislikeButton?.isClickable = false
        } else{
            binding?.dislikeButton?.setOnClickListener {
                if (!isDislikeSelected) {
                    isDislikeSelected = true
                    binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_selected)
                    binding?.dislikes?.setTextColor(Color.BLUE)
                    binding?.dislikes?.text = numDislikes.toInt().dec().toString()
                } else {
                    isDislikeSelected = false
                    binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)
                    binding?.dislikes?.setTextColor(Color.GRAY)
                    binding?.dislikes?.text = numDislikes
                }

            }
        }
    }*/

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}