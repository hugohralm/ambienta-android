package br.com.oversight.ambienta.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.databinding.FragmentHomeBinding
import br.com.oversight.ambienta.di.BaseFragment
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.model.Denuncia
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.ui.dialogSearch.BottomSheetSearch
import br.com.oversight.ambienta.utils.CodDenunciaHandler
import br.com.oversight.ambienta.utils.SwipeToDeleteCallback
import br.com.oversight.ambienta.utils.extensions.showSnack
import com.google.android.material.snackbar.Snackbar


@RequiresViewModel(HomeViewModel::class)
class HomeFragment : BaseFragment<HomeViewModel>(), DenunciaListAdapter.DenunciaCallbacks,
    SwipeToDeleteCallback.SwipeActions {
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
            ItemTouchHelper(SwipeToDeleteCallback(this@HomeFragment, requireContext(), denunciaListAdapter)).attachToRecyclerView(recyclerView)
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
        CodDenunciaHandler.addCodigo(codigo)
        viewModel?.fetchDenuncias()
    }

    override fun onItemClick(denuncia: Denuncia, position: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavHomeToDetalheDenunciaFragment(
                denuncia
            )
        )
    }

    override fun onSwipeToLeft(position: Int) {
        delete(position)
    }

    override fun onSwipeToRight(position: Int) {
        delete(position)
    }

    private fun delete(position: Int) {
        val denuncia = denunciaListAdapter.getItemAtPosition(position)
        CodDenunciaHandler.removeCodigo(denuncia.codigoAcompanhamento!!)
        viewModel?.fetchDenuncias()

        val snackbar: Snackbar = Snackbar.make(
            binding.root, "Denúncia removida",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Desfazer") { v ->
            run {
                CodDenunciaHandler.addCodigo(denuncia.codigoAcompanhamento!!)
                viewModel?.fetchDenuncias()
            }
        }
        snackbar.show()
    }
}