package com.android_academy.offline_first_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android_academy.offline_first_app.R
import com.android_academy.offline_first_app.StarWarsApp
import com.android_academy.offline_first_app.models.Person
import javax.inject.Inject


class MainFragment : Fragment() {

    private val dataAdapter: DataAdapter = DataAdapter { personId ->
        viewModel.onFavoriteClicked(personId)
    }

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
        val person = oldData[oldItemPosition]
        val newPerson = newData[newItemPosition]
        return person.personId == newPerson.personId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val person = oldData[oldItemPosition]
        val newPerson = newData[newItemPosition]
        return person.name == newPerson.name &&
                person.isFavor == newPerson.isFavor &&
                person.starships == newPerson.starships &&
                person.vehicles == newPerson.vehicles &&
                person.species == newPerson.species &&
                person.films == newPerson.films
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldPerson = oldData[oldItemPosition]
        val newPerson = newData[newItemPosition]
        if (oldPerson.personId == newPerson.personId) {
            return if (oldPerson.isFavor == newPerson.isFavor) {
                super.getChangePayload(oldItemPosition, newItemPosition)
            } else {
                val diff = Bundle()
                diff.putBoolean(ARG_IS_FAVOR, newPerson.isFavor)
                diff
            }
        }

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    companion object {
        const val ARG_IS_FAVOR = "arg_is_favor"
    }
}

class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
    val filmsTextView: TextView = itemView.findViewById(R.id.tv_films_name)
    val specieTextView: TextView = itemView.findViewById(R.id.tv_specie_name)
    val starshipsTextView: TextView = itemView.findViewById(R.id.tv_startships)
    val favoriteIcon: ImageView = itemView.findViewById(R.id.iv_favorite)


    fun update(bundle: Bundle) {
        if (bundle.containsKey(DiffCallback.ARG_IS_FAVOR)) {
            val isFavor = bundle.getBoolean(DiffCallback.ARG_IS_FAVOR)
            setFavoriteIcon(isFavor)
        }
    }

    fun bind(person: Person) {
        nameTextView.text = person.name
        filmsTextView.text = person.films.joinToString("\n") { it.title }
        specieTextView.text = person.species.firstOrNull()?.name
        starshipsTextView.text = person.starships.joinToString("\n") { it.name }
        setFavoriteIcon(person.isFavor)
    }

    private fun setFavoriteIcon(isFavor: Boolean) {
        val drawableRes = if (isFavor) {
            R.drawable.outline_star_24
        } else {
            R.drawable.outline_star_outline_24
        }
        favoriteIcon.setImageResource(drawableRes)
    }
}

class DataAdapter(private val onFavoriteClickListener: (personId: Int) -> Unit) :
    RecyclerView.Adapter<DataViewHolder>() {
    private var dataSet: List<Person> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        return DataViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DataViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isEmpty() || payloads[0] !is Bundle) {
            holder.bind(dataSet[position])
        } else {
            holder.update(payloads[0] as Bundle)
        }
        holder.favoriteIcon.setOnClickListener {
            onFavoriteClickListener.invoke(dataSet[position].personId)
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        onBindViewHolder(holder, position, emptyList())
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