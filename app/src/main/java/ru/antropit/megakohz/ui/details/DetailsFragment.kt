package ru.antropit.megakohz.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.details_fragment.*

import ru.antropit.megakohz.R
import ru.antropit.megakohz.api.ApiRepo
import ru.antropit.megakohz.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        DetailsFragmentBinding.bind(view!!).viewModel = viewModel

        activity?.title = DetailsViewModel.name.get()

        setPhoto()

        viewModel.getMedia().observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.setDetails(it)
                setLocation()
            }
        })

        lifecycle.addObserver(viewModel)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_frag) as? SupportMapFragment
        mapFragment?.getMapAsync(this@DetailsFragment)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setLocation()
    }

    // Add a marker in location and move the camera
    private fun setLocation() {
        if(DetailsViewModel.checkLocationIsInitialized()) {
            mMap.addMarker(MarkerOptions().position(DetailsViewModel.location).title(DetailsViewModel.name.get()))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DetailsViewModel.location, 10F))
        }
    }

    private fun setPhoto() {
        Glide.with(view?.context)
            .load(ApiRepo.API_URL + DetailsViewModel.img.get())
            .placeholder(R.drawable.placeholder)
            .into(details_photo)
    }
}
