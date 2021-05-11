package com.mcevoy.mcevoy_homeadvisor.view


import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mcevoy.mcevoy_homeadvisor.R
import com.mcevoy.mcevoy_homeadvisor.databinding.FragmentDetailBinding
import com.mcevoy.mcevoy_homeadvisor.model.ProData
import com.mcevoy.mcevoy_homeadvisor.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment: Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var proUuid = 0

    private lateinit var dataBinding: FragmentDetailBinding
    private var currentPro: ProData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            proUuid = DetailFragmentArgs.fromBundle(it).proUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(proUuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.proLiveData.observe(viewLifecycleOwner, Observer { pro ->
            currentPro = pro
            pro.let {
                dataBinding.pro = pro
            }
        })
    }

}