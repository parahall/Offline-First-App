package com.android_academy.custompagination.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_academy.custompagination.R

class MainFragment : Fragment() {

    private val dataAdapter: DataAdapter = DataAdapter()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.observeData(this) {
            val diffCallback = DiffCallback(dataAdapter.getData(), it)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            dataAdapter.setData(it)
            diffResult.dispatchUpdatesTo(dataAdapter)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = dataAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        return view
    }

}

private class DiffCallback(val oldData: List<String>, val newData: List<String>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
    }

}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.item_text_view)
}

class DataAdapter : RecyclerView.Adapter<DataViewHolder>() {
    private var dataSet: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.textView.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setData(data: List<String>) {
        dataSet = data
    }

    fun getData(): List<String> {
        return dataSet
    }
}