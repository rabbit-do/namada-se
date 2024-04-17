package net.namada.nebbstats.screen.submission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import net.namada.nebbstats.R
import net.namada.nebbstats.databinding.FragmentPlayerBinding
import net.namada.nebbstats.databinding.FragmentSubmissionOfPlayerBinding
import net.namada.nebbstats.models.SimplePlayer
import net.namada.nebbstats.screen.pilot.PilotFragmentDirections
import net.namada.nebbstats.screen.player.PlayerAdapter
import net.namada.nebbstats.screen.stats.StatsViewModel

/**
 *
 * create an instance of this fragment.
 */
class SubmissionOfPlayerFragment : Fragment() {
    private val argument: SubmissionOfPlayerFragmentArgs by navArgs()
    private val simplePlayer: SimplePlayer by lazy {
        argument.simplePlayer
    }
    private var _binding: FragmentSubmissionOfPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatsViewModel by activityViewModels()
    private var submissionAdapter: SubmissionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSubmissionOfPlayerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.simplePlayer = simplePlayer
        submissionAdapter = SubmissionAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = submissionAdapter
        }
        binding.playerAddress.text = simplePlayer.address
        binding.type.text = simplePlayer.type
        binding.button.setOnClickListener {
            findNavController().navigate(
                SubmissionOfPlayerFragmentDirections.actionSubmissionOfPlayerFragmentToExtendedFragment(simplePlayer.address)
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        submissionAdapter?.submissions = viewModel.getSubmissionOfPlayer(simplePlayer)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SubmissionOfPlayerFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}