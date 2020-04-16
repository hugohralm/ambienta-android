package br.com.oversight.ambienta.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.databinding.FragmentHomeBinding
import br.com.oversight.ambienta.di.BaseFragment
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.utils.extensions.makeToast
import com.google.gson.Gson

@RequiresViewModel(HomeViewModel::class)
class HomeFragment : BaseFragment<HomeViewModel>() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavHomeToPickLocationFragment())
        }
    }

    override fun bindViewModel(viewModel: HomeViewModel) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

    }
}