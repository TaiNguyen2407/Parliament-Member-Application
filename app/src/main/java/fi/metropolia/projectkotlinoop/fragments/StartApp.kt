package fi.metropolia.projectkotlinoop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import fi.metropolia.projectkotlinoop.R
import fi.metropolia.projectkotlinoop.databinding.FragmentStartBinding
import kotlin.system.exitProcess

/**
 * A simple Starting Fragment.
 */
class StartApp : Fragment() {
    private var binding: FragmentStartBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set navigation
        binding?.apply {
            //Set destination after clicking Start button on starting screen
            //Destination will be Party List Fragment
            startButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_partyList)
            }
            //Set action to quit the application whenever user click at Quit button on starting screen
            quitButton.setOnClickListener {
                Toast.makeText(requireContext(), "App Closed", Toast.LENGTH_SHORT).show()
                exitProcess(0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}