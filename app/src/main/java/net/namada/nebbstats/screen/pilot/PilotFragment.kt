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
import net.namada.nebbstats.screen.player.PlayerAdapter
import net.namada.nebbstats.screen.player.PlayerClick
import net.namada.nebbstats.screen.stats.StatsViewModel

class PilotFragment : Fragment() {

    private var _binding: FragmentPilotBinding? = null

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
        _binding = FragmentPilotBinding.inflate(inflater, container, false)
        val root: View = binding.root
        playerAdapter = PlayerAdapter(PlayerClick {
            findNavController().navigate(
                PilotFragmentDirections.actionNavigationPilotToNavigationPlayer()
            )
        })
        refreshLayout = root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)
        refreshLayout.setOnRefreshListener {
            viewModel.getPilotList(0)
        }
        root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = playerAdapter
            addOnScrollListener(object :
                EndlessScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.getPilotList(page)
                }
            })
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Pilot viewModel: "+ viewModel)
        viewModel.pilots.observe(viewLifecycleOwner){ pilots ->
            playerAdapter?.players = pilots

        }
        viewModel.getPilotList(0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}