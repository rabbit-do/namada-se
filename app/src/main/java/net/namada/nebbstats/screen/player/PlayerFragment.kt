package net.namada.nebbstats.screen.player

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
import net.namada.nebbstats.models.PlayerStat
import net.namada.nebbstats.screen.pilot.PilotFragmentDirections
import net.namada.nebbstats.screen.stats.StatsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [PlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayerFragment : Fragment() {
    private val argument: PlayerFragmentArgs by navArgs()
    val playerStat: PlayerStat by lazy {
        argument.player
    }
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel : StatsViewModel by activityViewModels()
    private var playerAdapter: PlayerAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.playerStat = playerStat
        playerAdapter = PlayerAdapter(PlayerClick {
            findNavController().navigate(
                PlayerFragmentDirections.actionNavigationPlayerToSubmissionOfPlayerFragment(it)
            )
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
        }
        if(playerStat.sClassCategoryCount == 1){
            binding.title.text =
                "List of players completed S Class in ${playerStat.sClassCategoryCount} category"
        }else {
            binding.title.text =
                "List of players completed S-class in ${playerStat.sClassCategoryCount} categories"
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        playerAdapter?.players = viewModel.getPlayerList(playerStat)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlayerFragment().apply {

            }
    }
}