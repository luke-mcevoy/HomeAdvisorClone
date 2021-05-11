package com.mcevoy.mcevoy_homeadvisor.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mcevoy.mcevoy_homeadvisor.R
import com.mcevoy.mcevoy_homeadvisor.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val proListAdapter = ProListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        proList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = proListAdapter
        }

        refreshLayout.setOnRefreshListener {
            proList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }


        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.pros.observe(viewLifecycleOwner, Observer { pros ->
            pros.let {
                proList.visibility = View.VISIBLE
                proListAdapter.updateProList(pros)
            }
        })

        viewModel.prosLoadingError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    proList.visibility = View.GONE
                }
            }
        })
    }


}