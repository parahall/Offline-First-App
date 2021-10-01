package com.android_academy.custompagination.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_academy.custompagination.R
import com.android_academy.custompagination.StarWarsApp
import com.android_academy.custompagination.models.Person
import javax.inject.Inject


class MainFragment : Fragment() {

    private val dataAdapter: DataAdapter = DataAdapter()

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StarWarsApp.getAppComponent().provideMainFragSubcomponent().create(this).inject(this)

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
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

}

private class DiffCallback(val oldData: List<Person>, val newData: List<Person>) :
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
    val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
    val filmsTextView: TextView = itemView.findViewById(R.id.tv_films_name)
    val specieTextView: TextView = itemView.findViewById(R.id.tv_specie_name)
    val starshipsTextView: TextView = itemView.findViewById(R.id.tv_startships)
}

class DataAdapter : RecyclerView.Adapter<DataViewHolder>() {
    private var dataSet: List<Person> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.nameTextView.text = dataSet[position].name
        holder.filmsTextView.text = dataSet[position].films.joinToString("\n") { it.title }
        holder.specieTextView.text = dataSet[position].species.firstOrNull()?.name
        holder.starshipsTextView.text = dataSet[position].starships.joinToString("\n") { it.name }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun setData(data: List<Person>) {
        dataSet = data
    }

    fun getData(): List<Person> {
        return dataSet
    }
}