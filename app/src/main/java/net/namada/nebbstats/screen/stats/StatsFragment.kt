package net.namada.nebbstats.screen.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.namada.nebbstats.R
import net.namada.nebbstats.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : StatsViewModel by activityViewModels()
    lateinit var viewModelAdapter: ClassifiedSubmissionAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val root: View = binding.root
        viewModelAdapter = ClassifiedSubmissionAdapter(ClassifiedSubmissionClick {
            println("click")
        }, "All")
        root.findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllSubmission()
        println("Stats viewModel: "+ viewModel)
        viewModel.cgsl.observe(viewLifecycleOwner){ cgsl ->
            cgsl?.apply {
                viewModelAdapter.submissionList = cgsl
            }

        }
    }
}