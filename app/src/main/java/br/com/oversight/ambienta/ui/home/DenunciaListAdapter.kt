package br.com.oversight.ambienta.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.oversight.ambienta.databinding.ItemDenunciaBinding
import br.com.oversight.ambienta.model.Denuncia

class DenunciaListAdapter(private val denunciaListener: DenunciaCallbacks) :
    ListAdapter<Denuncia, DenunciaListAdapter.ViewHolder>(PostDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val denuncia = getItem(position)

        holder.apply {
            bind(denuncia, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id?.toLong() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            denunciaListener,
            ItemDenunciaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    class ViewHolder(
        private val listener: DenunciaCallbacks,
        private val binding: ItemDenunciaBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Denuncia, index: Int) {
            binding.apply {
                denuncia = item
                card.setOnClickListener { listener.onItemClick(item, index) }
                status.setBackgroundColor(Color.parseColor(item.status?.color))
                executePendingBindings()
            }
        }
    }

    private class PostDiffCallback : DiffUtil.ItemCallback<Denuncia>() {

        override fun areItemsTheSame(oldItem: Denuncia, newItem: Denuncia): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Denuncia, newItem: Denuncia): Boolean {
            return oldItem == newItem
        }
    }

    interface DenunciaCallbacks {
        fun onItemClick(denuncia: Denuncia, position: Int)
    }
}