package net.namada.nebbstats.screen.pilot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.namada.nebbstats.R
import net.namada.nebbstats.common.EndlessScrollListener
import net.namada.nebbstats.databinding.FragmentPilotBinding
import net.namada.nebbstats.models.PlayerStat
import net.namada.nebbstats.screen.crew.CrewFragmentDirections
import net.namada.nebbstats.screen.player.PlayerAdapter
import net.namada.nebbstats.screen.player.PlayerClick
import net.namada.nebbstats.screen.stats.StatsViewModel

class PilotFragment : Fragment() {

    private var _binding: FragmentPilotBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel : StatsViewModel by activityViewModels()
//    private var playerAdapter: PlayerAdapter? = null
    private lateinit var refreshLayout: SwipeRefreshLayout
    private var playerStatAdapter: PlayerStatAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPilotBinding.inflate(inflater, container, false)
        val root: View = binding.root

        playerStatAdapter = PlayerStatAdapter( PlayerStatClick {
            findNavController().navigate(
                PilotFragmentDirections.actionNavigationPilotToNavigationPlayer(it)
            )
        })
        root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerStatAdapter

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Pilot viewModel: "+ viewModel)
        viewModel.pilotPlayerStat.observe(viewLifecycleOwner){ pilotStat ->
            playerStatAdapter?.playerStats = pilotStat

        }
        viewModel.getPilotList(0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}