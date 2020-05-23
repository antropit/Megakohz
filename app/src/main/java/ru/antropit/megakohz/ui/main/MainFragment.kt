package ru.antropit.megakohz.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import ru.antropit.megakohz.ui.details.DetailsFragment

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        lateinit var adapter: MainAdapter
    }

    private lateinit var viewModel: MainViewModel

    init {
        adapter = MainAdapter(listener = { entity ->
            viewModel.onClickItem(entity)
            activity?.supportFragmentManager?.beginTransaction()!!
                .replace(ru.antropit.megakohz.R.id.container, DetailsFragment.newInstance())
                .addToBackStack(null)
                .commit()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTitle(ru.antropit.megakohz.R.string.app_name)
        return inflater.inflate(ru.antropit.megakohz.R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvKhz.setHasFixedSize(true)
        rvKhz.layoutManager = LinearLayoutManager(context)
        rvKhz.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getMedia().observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        lifecycle.addObserver(viewModel)
    }
}
