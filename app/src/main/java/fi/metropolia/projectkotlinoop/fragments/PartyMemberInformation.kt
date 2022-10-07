package fi.metropolia.projectkotlinoop.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.data.MemberLikes
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberInformationBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModelFactory


/**
 * A Fragment that shows selected member basic information
 * It also shows the like/dislike that user give to mentioned member
 */
class PartyMemberInformation : Fragment() {
    private var binding: FragmentPartyMemberInformationBinding? = null
    //Initializing Safe Args to receive selected member sent from PartyMemberList Fragment (origin destination)
    private val safeArgs: PartyMemberInformationArgs by navArgs()
    //Initialize View Model
    private val partyMemberInformationViewModel: PartyMemberInformationViewModel by viewModels {
        PartyMemberInformationViewModelFactory()
    }
    private var isDislikeSelected = false
    private var isLikeSelected = false


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
        /**
         * Block of initializations are just to easier set content of select item in XML file
         */
        val isMemberAMinister = safeArgs.chosenMember.minister.toString()
        val memberFullName =
            safeArgs.chosenMember.firstname.uppercase() + " " + safeArgs.chosenMember.lastname.uppercase()
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


        /**
         *Code block is to implement likes/dislikes features in Member Fragment
         * Feature will show like/dislike numbers of each member
         * Mirroring Facebook/Youtube like feature.
         */
        //Set HetekaId for selected member
        partyMemberInformationViewModel.setHetekaId(memberHetekaId.toInt())

        partyMemberInformationViewModel.insertLikesData(MemberLikes(memberHetekaId.toInt(), 0, 0))

        //Observe pattern for number of likes shown on UI
        partyMemberInformationViewModel.likesNum.observe(viewLifecycleOwner) {
            binding?.likes?.text = it.toString()
        }
        //Observe pattern for number of dislikes shown on UI
        partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner) {
            binding?.dislikes?.text = it.toString()
        }

        //Set like button image
        binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)

        //Set click listener for Like button
        binding?.likeButton?.setOnClickListener {
            if (!isLikeSelected) {
                isLikeSelected = true
                binding?.likeButton?.setImageResource(R.drawable.thumbs_up_selected)
                binding?.likes?.setTextColor(Color.BLUE)
                partyMemberInformationViewModel.likesNum.observe(viewLifecycleOwner) {
                    binding?.likes?.text = it.inc().toString()
                }
                if (isDislikeSelected) {
                    binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)
                    binding?.dislikes?.setTextColor(Color.GRAY)
                    partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner) {
                        binding?.dislikes?.text = it.toString()
                    }
                }
            }

        }
        //Set dislike button Image
        binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)

        binding?.dislikeButton?.setOnClickListener {
            if (!isDislikeSelected) {
                isDislikeSelected = true
                binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_selected)
                binding?.dislikes?.setTextColor(Color.BLUE)
                partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner) {
                    binding?.dislikes?.text = it.inc().toString()
                }
                if (isLikeSelected) {
                    binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)
                    binding?.likes?.setTextColor(Color.GRAY)
                    partyMemberInformationViewModel.likesNum.observe(viewLifecycleOwner) {
                        binding?.likes?.text = it.toString()
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}