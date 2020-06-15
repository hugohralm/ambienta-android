package br.com.oversight.ambienta.ui.detalheDenuncia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.oversight.ambienta.databinding.ItemHistoricoBinding
import br.com.oversight.ambienta.model.RespostaDenuncia

class HistoricoListAdapter :
    RecyclerView.Adapter<HistoricoListAdapter.ViewHolder>() {

    private var list: MutableList<RespostaDenuncia> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoricoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun submitList(list: List<RespostaDenuncia>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ItemHistoricoBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(respostaDenuncia: RespostaDenuncia) {
            binding.apply {
                item = respostaDenuncia
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int = list.size

}