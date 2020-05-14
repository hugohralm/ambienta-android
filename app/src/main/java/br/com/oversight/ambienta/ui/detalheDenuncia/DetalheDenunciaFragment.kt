package br.com.oversight.ambienta.ui.detalheDenuncia

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.databinding.FragmentDetalheDenunciaBinding
import br.com.oversight.ambienta.di.BaseFragment
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.ui.home.DetalheEvidenciasListAdapter
import br.com.oversight.ambienta.utils.WorkaroundMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
@RequiresViewModel(DetalheDenunciaViewModel::class)
class DetalheDenunciaFragment : BaseFragment<DetalheDenunciaViewModel>(), OnMapReadyCallback {
    val args: DetalheDenunciaFragmentArgs by navArgs()
    val adapter: DetalheEvidenciasListAdapter = DetalheEvidenciasListAdapter()
    lateinit var binding: FragmentDetalheDenunciaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalheDenunciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        this.binding.recyclerView.adapter = this.adapter

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun bindViewModel(viewModel: DetalheDenunciaViewModel) {
        this.binding.vm = viewModel
        viewModel.denuncia.value = args.denuncia
        this.adapter.submitList(viewModel.denuncia.value!!.evidencias)
    }

    override fun onMapReady(map: GoogleMap) {
        this.viewModel?.denuncia?.value?.let {
            val lng = LatLng(it.latitude!!, it.longitude!!)
            map.addMarker(MarkerOptions().position(lng).title(it.titulo))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 17f))
            enableMyLocation(map)

            (childFragmentManager.findFragmentById(R.id.map) as WorkaroundMapFragment)
                .setListener(object :
                    WorkaroundMapFragment.OnTouchListener {
                    override fun onTouch() {
                        binding.scroll.requestDisallowInterceptTouchEvent(true)
                    }
                })
        }
    }

    @NeedsPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun enableMyLocation(map: GoogleMap) {
        map.isMyLocationEnabled = true;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}