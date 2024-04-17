package net.namada.nebbstats.screen.extended

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import net.namada.nebbstats.R
import net.namada.nebbstats.models.PlayerStat
import net.namada.nebbstats.screen.player.PlayerFragmentArgs


/**
 * A simple [Fragment] subclass.
 * Use the [ExtendedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExtendedFragment : Fragment() {
    private val argument: ExtendedFragmentArgs by navArgs()
    val address: String by lazy {
        argument.address
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_extended, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView = view.findViewById<WebView>(R.id.webView)
        webView.loadUrl("https://extended-nebb.kintsugi.tech/player/$address")
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExtendedFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}