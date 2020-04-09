package br.com.oversight.ambienta.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.model.DenunciaResponse
import br.com.oversight.ambienta.utils.extensions.makeToast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.nav_header_main.*

class HomeFragment : Fragment(), HomeView {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.setProtocol(this)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        getById("2")
        return root
    }

    private fun getById(id: String) {
        id.let {
            homeViewModel.getById(id).observe(this, Observer { response ->
                response.let { resp ->
                    val denuncia = Gson().fromJson(resp, DenunciaResponse::class.java)
                    activity!!.makeToast(denuncia.titulo!!)
                }
            })
        }
    }

    override fun responseError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface HomeView {
    fun responseError(error: String)
}
