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
    //Dislike state at first is false, if user clicks on button, it turns to true
    private var isDislikeSelected = false
    //Like state at first is false, if user clicks on button, it turns to true
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
        //Insert initial like/dislike data (which is 0) to selected member
        partyMemberInformationViewModel.insertLikesData(MemberLikes(memberHetekaId.toInt(), 0, 0))

        //Observe pattern for number of likes shown on UI
        partyMemberInformationViewModel.likesNum.observe(viewLifecycleOwner) {
            binding?.likes?.text = it.toString()
        }
        //Observe pattern for number of dislikes shown on UI
        partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner) {
            binding?.dislikes?.text = it.toString()
        }
        /**
         * Block of code below implemented following feature:
         * When user click like button, number of likes turns from 0 to 1
         * Like icon changes from white image and black number to black image and blue number
         * It indicates that the like button has been clicked and database has been notified
         * However, when user changes his/her mind, to dislike selected member
         * He/she can click dislike button and dislike number and icon will change same way as like button did
         * The feature is created so that user can only like or dislike a member, there is no possibility to like and dislike a member at the same time
         * Also the feature allows 1 like / 1 dislike person
         * It imitates Like feature in Facebook UX
         */
        //Set initial like button image/state
        binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)

        //Set click listener for Like button
        binding?.likeButton?.setOnClickListener {
            //If like is clicked, then set to true
            if (!isLikeSelected) {
                isLikeSelected = true
                //Set like button image to black like image
                binding?.likeButton?.setImageResource(R.drawable.thumbs_up_selected)
                //Set color of like number to blue -> indicated has been clicked
                binding?.likes?.setTextColor(Color.BLUE)
                //Observe and update regularly the like number when user clicks (plus 1)
                partyMemberInformationViewModel.likesNum.observe(viewLifecycleOwner) {
                    binding?.likes?.text = it.inc().toString()
                }

                //If like button is clicked
                if (isDislikeSelected) {
                    //Immediately set the dislike button to unselected
                    binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)
                    //Set dislike number to gray color
                    binding?.dislikes?.setTextColor(Color.GRAY)
                    //Observe and update dislike number
                    partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner) {
                        binding?.dislikes?.text = it.toString()
                    }
                }
            }
        }
        //Set initial dislike button Image/state
        binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)

        binding?.dislikeButton?.setOnClickListener {
            //If dislike is clicked, then set to true
            if (!isDislikeSelected) {
                isDislikeSelected = true
                //Set dislike button image to black like image
                binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_selected)
                //Set color of dislike number to blue -> Indicate has been click
                binding?.dislikes?.setTextColor(Color.BLUE)
                //Observe and update regularly the dislike number when user clicks (plus 1)
                partyMemberInformationViewModel.dislikesNum.observe(viewLifecycleOwner) {
                    binding?.dislikes?.text = it.inc().toString()
                }
                //Check if like button is clicked, if yes
                if (isLikeSelected) {
                    //Immediately set the like button unselected
                    binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)
                    //Set like number to gray color
                    binding?.likes?.setTextColor(Color.GRAY)
                    //Observe and update like numbers
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