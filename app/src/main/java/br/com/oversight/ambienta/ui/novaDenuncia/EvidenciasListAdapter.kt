package br.com.oversight.ambienta.ui.novaDenuncia

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.databinding.ItemEvidenciaBinding
import coil.api.load
import coil.transform.RoundedCornersTransformation
import pl.aprilapps.easyphotopicker.MediaFile
import timber.log.Timber
import java.io.File
import java.util.*

class EvidenciasListAdapter(private val listener: EvidenciasAdapterCallbacks) :
    RecyclerView.Adapter<EvidenciasListAdapter.ViewHolder>() {

    private val list: MutableList<MediaFile> = mutableListOf(MediaFile(Uri.EMPTY, File("")))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.apply {
            bind(
                item,
                if (position == list.lastIndex) ResourceType.ADD_IMAGEPLACE_HOLDER else ResourceType.PHOTO,
                listener
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

    class ViewHolder(
        private val binding: ItemEvidenciaBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            mediaFile: MediaFile,
            resType: ResourceType,
            listener: EvidenciasAdapterCallbacks
        ) {
            Timber.d("bind item")
            binding.apply {
                if (resType == ResourceType.ADD_IMAGEPLACE_HOLDER) {
                    image.load(R.drawable.add_image)
                    root.setOnClickListener {
                        listener.addEvidencia()
                    }
                } else {
                    image.load(mediaFile.file) {
                        transformations(RoundedCornersTransformation(15f))
                    }
                }
                type = resType
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun removeItemAtIndex(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun addItem(mediaFile: MediaFile) {
//        if (!containItem(mediaFile)) {
            list.add(mediaFile)
            Collections.swap(list, list.size - 2, list.size - 1)
            notifyDataSetChanged()
//        }
    }

    private fun containItem(mediaFile: MediaFile): Boolean {

        list.forEach {
            if (it.file.absolutePath == mediaFile.file.absolutePath)
                return true
        }
        return false
    }

    fun getEvidencias() = list.filter { mediaFile -> mediaFile.file.path !== "" }

    interface EvidenciasAdapterCallbacks {
        fun addEvidencia()
    }

    enum class ResourceType {
        PHOTO,
        ADD_IMAGEPLACE_HOLDER
    }
}