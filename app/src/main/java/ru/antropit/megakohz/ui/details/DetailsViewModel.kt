package ru.antropit.megakohz.ui.details

import androidx.lifecycle.ViewModel
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.android.gms.maps.model.LatLng
import io.reactivex.schedulers.Schedulers
import ru.antropit.megakohz.api.ApiRepo
import ru.antropit.megakohz.api.model.Details

class DetailsViewModel : ViewModel(), Observable, LifecycleObserver {
    companion object {
        private val registry = PropertyChangeRegistry()
        @Bindable lateinit var id: String
        @Bindable lateinit var name: ObservableField<String>
        @Bindable lateinit var description: ObservableField<String>
        @Bindable lateinit var img: ObservableField<String>
        @Bindable lateinit var www: ObservableField<String>
        @Bindable lateinit var phone: ObservableField<String>
        @Bindable lateinit var location: LatLng

        fun checkLocationIsInitialized() : Boolean {
            return ::location.isInitialized
        }   }

    fun getMedia() = LiveDataReactiveStreams.fromPublisher(
        ApiRepo.createAdapter().loadDetails(id.toInt())
            .observeOn(Schedulers.io())
            .onErrorReturn { e: Throwable -> listOf(Details(id.toInt(), name.toString(), img.toString(), e.toString(), 0.0, 0.0, "", "")) }
    )

    fun setDetails(listData : List<Details>) {
        listData.first().let {
            name.set(it.name)
            description.set(it.description)
            img.set(it.img)
            www.set(it.www)
            phone.set(it.phone)
            location = LatLng(it.lat, it.lon)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }
}
