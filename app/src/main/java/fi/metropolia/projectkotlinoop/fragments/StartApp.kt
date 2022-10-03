package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartApp.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartApp : Fragment() {
    private var binding: FragmentStartBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            startButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_partyList)
            }
            quitButton.setOnClickListener {
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}