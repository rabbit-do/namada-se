package net.namada.nebbstats.screen.crew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import net.namada.nebbstats.R
import net.namada.nebbstats.common.EndlessScrollListener
import net.namada.nebbstats.databinding.FragmentCrewBinding
import net.namada.nebbstats.screen.pilot.PilotFragmentDirections
import net.namada.nebbstats.screen.pilot.PilotViewModel
import net.namada.nebbstats.screen.stats.PlayerAdapter
import net.namada.nebbstats.screen.stats.PlayerClick
import net.namada.nebbstats.screen.stats.StatsViewModel

class CrewFragment : Fragment() {

    private var _binding: FragmentCrewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel : StatsViewModel by activityViewModels()
    private var playerAdapter: PlayerAdapter? = null
    private lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrewBinding.inflate(inflater, container, false)
        val root: View = binding.root
        playerAdapter = PlayerAdapter(PlayerClick {
            findNavController().navigate(
                CrewFragmentDirections.actionNavigationCrewToNavigationPlayer()
            )
        })
        refreshLayout = root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener {
            viewModel.getCrewList(0)
        }
        root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
            addOnScrollListener(object :
                EndlessScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getCrewList(page)
                }
            })
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCrewList(0)
        viewModel.crews.observe(viewLifecycleOwner){ crews ->
            playerAdapter?.players = crews

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}