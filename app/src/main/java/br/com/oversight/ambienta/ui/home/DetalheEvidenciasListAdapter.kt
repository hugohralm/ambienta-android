package br.com.oversight.ambienta.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.StringUtil
import br.com.oversight.ambienta.databinding.ItemEvidenciaBinding
import br.com.oversight.ambienta.model.Evidencia
import br.com.oversight.ambienta.ui.novaDenuncia.EvidenciasListAdapter
import coil.api.load
import coil.transform.RoundedCornersTransformation
import timber.log.Timber

class DetalheEvidenciasListAdapter :
    RecyclerView.Adapter<DetalheEvidenciasListAdapter.ViewHolder>() {

    private var list: MutableList<Evidencia> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            bind(
                item
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEvidenciaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun submitList(list: List<Evidencia>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemEvidenciaBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            evidencia: Evidencia
        ) {
            binding.apply {

                image.load(evidencia.url?.replace("http:", "https:")) {
                    transformations(RoundedCornersTransformation(15f))
                }
                type = EvidenciasListAdapter.ResourceType.PHOTO
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int = list.size

}