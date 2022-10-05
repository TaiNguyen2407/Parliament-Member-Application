package fi.metropolia.projectkotlinoop.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fi.metropolia.projectkotlinoop.MemberApplication
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.databinding.FragmentPartyMemberInformationBinding
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModel
import fi.metropolia.projectkotlinoop.viewmodel.PartyMemberInformationViewModelFactory
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt
import kotlin.random.nextInt

/**
 * A simple [Fragment] subclass.
 * Use the [PartyMemberInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class PartyMemberInformation : Fragment() {
    private var binding: FragmentPartyMemberInformationBinding? = null
    private val safeArgs: PartyMemberInformationArgs by navArgs()
    private var isSelected: Boolean = false


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
        val memberFullName =
            safeArgs.chosenMember.firstname.uppercase() + " " + safeArgs.chosenMember.lastname.uppercase()
        val memberParty = safeArgs.chosenMember.party.uppercase()
        val memberHetekaId = safeArgs.chosenMember.hetekaId.toString()
        val memberSeatNumber = safeArgs.chosenMember.seatNumber.toString()
        val memberPicture = safeArgs.chosenMember.pictureUrl
        val numLike = Random.nextInt(0..100).toString()
        val numDislikes = Random.nextInt(-100..0).toString()

        binding?.let {
            Glide.with(this).load("https://avoindata.eduskunta.fi/" + memberPicture)
                .apply(RequestOptions.centerInsideTransform())
                .into(it.imageView)
        }
        binding?.hetekaid?.text = "Heteka Id: $memberHetekaId"
        binding?.party?.text = "Party: $memberParty"
        binding?.seatNumber?.text = "Seat Number: $memberSeatNumber"
        if ((isMemberAMinister) == "true") {
            binding?.fullName?.text = "Minister $memberFullName"
        } else {
            binding?.fullName?.text = memberFullName
        }


        binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)
        binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)
        binding?.likes?.text = numLike
        binding?.dislikes?.text = numDislikes
        updateLikeStatus()
        updateDislikeStatus()
    }

    fun updateLikeStatus() {
        binding?.likeButton?.findViewById<ImageButton>(R.id.likeButton)?.setOnClickListener {
            if (!isSelected) {
                isSelected = true
                binding?.likeButton?.setImageResource(R.drawable.thumbs_up_selected)
                binding?.likes?.setTextColor(Color.BLUE)
            } else {
                isSelected = false
                binding?.likeButton?.setImageResource(R.drawable.thumbs_up_unselected)
                binding?.likes?.setTextColor(Color.GRAY)
            }
        updateLikeNumber()
        }
    }
    fun updateDislikeStatus() {
        binding?.dislikeButton?.findViewById<ImageButton>(R.id.dislikeButton)?.setOnClickListener {
            if (!isSelected) {
                binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_selected)
                binding?.dislikes?.setTextColor(Color.BLUE)
                isSelected = true
            } else {
                binding?.dislikeButton?.setImageResource(R.drawable.thumbs_down_unselected)
                binding?.dislikes?.setTextColor(Color.GRAY)
                isSelected = false
            }
        updateDislikeNumber()
        }
    }

    private fun updateLikeNumber(){
        //binding?.likes?.setText(Integer.parseInt(binding?.))
    }
    private fun updateDislikeNumber(){

    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}