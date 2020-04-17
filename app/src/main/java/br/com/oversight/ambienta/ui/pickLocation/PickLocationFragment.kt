package br.com.oversight.ambienta.ui.pickLocation

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.databinding.FragmentPickLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions
import timber.log.Timber


@RuntimePermissions
class PickLocationFragment : Fragment(), OnMapReadyCallback {

    private val mDefaultLocation: LatLng = LatLng(-16.797638, -49.533607)
    private val DEFAULT_ZOOM: Float = 17f
    private lateinit var binding: FragmentPickLocationBinding
    lateinit var bitmapDescriptor: BitmapDescriptor

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var mMap: GoogleMap

    private var targetLocation: LatLng? = null
    var mLastLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bitmapDescriptor = BitmapDescriptorFactory.defaultMarker()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        fetchLocationWithPermissionCheck()
        binding.confirm.setOnClickListener {
           findNavController().navigate( PickLocationFragmentDirections.actionPickLocationFragmentToNovaDenunciaFragment(
               targetLocation!!
           ))
        }
    }

    @NeedsPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun fetchLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                mMap.isMyLocationEnabled = true
                mLastLocation = it
                Timber.d("Current location get. Location: ${mLastLocation?.latitude}, ${mLastLocation?.longitude}")
                mLastLocation?.let { location ->
                    mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                location.latitude,
                                location.longitude
                            ), DEFAULT_ZOOM
                        )
                    )
                }
            }.addOnFailureListener {
                Timber.d("Current location failure. Using defaults.")
                Timber.e("Exception: $it")
                moveToDefaultLocation()
            }
        } catch (e: SecurityException) {
            Timber.e("Exception: %s", e.message)
        }
    }

    @OnPermissionDenied(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun moveToDefaultLocation() {
        Timber.d("moveToDefaultLocation() called")
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mDefaultLocation,
                5f
            )
        )
        mMap.isMyLocationEnabled = false
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        val density = context!!.resources.displayMetrics.density
        mMap.setPadding(0, 0, 0, (65 * density).toInt())
        mMap.isBuildingsEnabled = false
        mMap.setOnCameraIdleListener {
            targetLocation = mMap.cameraPosition.target
            Timber.i("TARGET LOCATION UPDATED: ${targetLocation?.latitude} , ${targetLocation?.longitude}")
        }
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
