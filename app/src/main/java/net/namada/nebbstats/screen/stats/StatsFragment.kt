package net.namada.nebbstats.screen.stats

import android.app.ProgressDialog
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
    private var progressDialog: ProgressDialog? = null

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
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Data loading...")
        progressDialog?.show()
        viewModel.getAllSubmission()
        println("Stats viewModel: "+ viewModel)
        viewModel.cgsl.observe(viewLifecycleOwner){ cgsl ->
            progressDialog?.dismiss()
            cgsl?.apply {
                viewModelAdapter.submissionList = cgsl
            }
        }
    }
}