package com.mcevoy.mcevoy_homeadvisor.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mcevoy.mcevoy_homeadvisor.R
import com.mcevoy.mcevoy_homeadvisor.databinding.ItemProBinding
import com.mcevoy.mcevoy_homeadvisor.model.ProData
import kotlinx.android.synthetic.main.item_pro.view.*

class ProListAdapter(private val proList: ArrayList<ProData>): RecyclerView.Adapter<ProListAdapter.ProViewHolder>(),
    ProClickListener {

    fun updateProList(newProList: List<ProData>) {
        proList.clear()
        proList.addAll(newProList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemProBinding>(inflater, R.layout.item_pro, parent, false)
        return ProViewHolder(view)
    }

    override fun getItemCount() = proList.size

    override fun onBindViewHolder(holder: ProViewHolder, position: Int) {
        holder.view.pro = proList[position]
        holder.view.listener = this
    }

    override fun onProClicked(view: View) {
        val uuid = view.proId.text.toString().toInt()
        val action = ListFragmentDirections.actionDetailFragment()
        action.proUuid = uuid
        Navigation.findNavController(view).navigate(action)
    }

    class ProViewHolder(var view: ItemProBinding) : RecyclerView.ViewHolder(view.root)
}