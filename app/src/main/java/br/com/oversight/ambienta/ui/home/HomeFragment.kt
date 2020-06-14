package br.com.oversight.ambienta.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.databinding.FragmentHomeBinding
import br.com.oversight.ambienta.di.BaseFragment
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.ui.dialogSearch.BottomSheetSearch
import br.com.oversight.ambienta.utils.DENUNCIA_PREFERENCES
import br.com.oversight.ambienta.utils.extensions.showSnack
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.launch

@RequiresViewModel(HomeViewModel::class)
class HomeFragment : BaseFragment<HomeViewModel>(), DenunciaListAdapter.DenunciaCallbacks {
    lateinit var binding: FragmentHomeBinding
    var denunciaListAdapter: DenunciaListAdapter = DenunciaListAdapter(this)

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
        binding.apply {
            floatingActionButton.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionNavHomeToPickLocationFragment())
            }
            floatingActionButtonSearch.setOnClickListener {
                BottomSheetSearch(object : BottomSheetSearch.BottomSheetSearchActions {
                    override fun salvar(codigo: String) {
                        inserirCodigoDenuncia(codigo)
                    }
                }).show(childFragmentManager, "searchDialog")
            }

            recyclerView.adapter = denunciaListAdapter
            swipe.apply {
                setOnRefreshListener {
                    isRefreshing = false
                    viewModel!!.fetchDenuncias()
                }
                setColorSchemeColors(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    ContextCompat.getColor(context, R.color.colorAccent)
                )
            }
        }
    }

    override fun bindViewModel(viewModel: HomeViewModel) {
        binding.apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        viewModel.denunciaList.observe(this, Observer {
            when (it.status) {
                ApiResult.Status.STATUS_SUCCESS -> {
                    denunciaListAdapter.submitList(it.data)
                }
                ApiResult.Status.STATUS_ERROR -> showSnack(binding.root, "Erro: ${it.errorMessage}")
            }
        })
    }

    fun inserirCodigoDenuncia(codigo: String) {
        val array = Hawk.get<MutableList<String>>(DENUNCIA_PREFERENCES) ?: mutableListOf()
        array.add(codigo)
        Hawk.put(DENUNCIA_PREFERENCES, array)
        viewModel?.fetchDenuncias()
    }

    override fun onItemClick(denuncia: Denuncia, position: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavHomeToDetalheDenunciaFragment(
                denuncia
            )
        )
    }
}