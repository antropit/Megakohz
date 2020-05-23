package ru.antropit.megakohz.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import io.reactivex.schedulers.Schedulers
import ru.antropit.megakohz.api.ApiRepo
import ru.antropit.megakohz.api.model.Entity
import ru.antropit.megakohz.ui.details.DetailsViewModel

class MainViewModel : ViewModel(), LifecycleObserver {

    fun getMedia() = LiveDataReactiveStreams.fromPublisher(
        ApiRepo.createAdapter().loadTestQuery()
            .observeOn(Schedulers.io())
            .onErrorReturn { e: Throwable -> listOf(Entity(1, e.toString(), "")) }
    )

    fun onClickItem(data: Entity) {
        DetailsViewModel.id = data.id.toString()
        DetailsViewModel.name = ObservableField(data.name)
        DetailsViewModel.img = ObservableField(data.img)
        DetailsViewModel.description = ObservableField("")
        DetailsViewModel.www = ObservableField("")
        DetailsViewModel.phone = ObservableField("")
        DetailsViewModel.location = LatLng(0.0, 0.0)
    }


}
