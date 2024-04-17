package net.namada.nebbstats.screen.crew

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
import net.namada.nebbstats.databinding.FragmentCrewBinding
import net.namada.nebbstats.databinding.FragmentPilotBinding
import net.namada.nebbstats.models.PlayerStat
import net.namada.nebbstats.screen.pilot.PlayerStatAdapter
import net.namada.nebbstats.screen.pilot.PlayerStatClick
import net.namada.nebbstats.screen.player.PlayerAdapter
import net.namada.nebbstats.screen.player.PlayerClick
import net.namada.nebbstats.screen.stats.StatsViewModel

class CrewFragment : Fragment() {

    private var _binding: FragmentCrewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel : StatsViewModel by activityViewModels()
    private var playerAdapter: PlayerAdapter? = null
    private lateinit var refreshLayout: SwipeRefreshLayout
    private var playerStatAdapter: PlayerStatAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        playerStatAdapter = PlayerStatAdapter( PlayerStatClick { it ->
            findNavController().navigate(
                CrewFragmentDirections.actionNavigationCrewToNavigationPlayer(it)
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
        viewModel.getCrewList(0)
        viewModel.crewPlayerStat.observe(viewLifecycleOwner){ crewStat ->
            playerStatAdapter?.playerStats = crewStat

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}