package pnj.uas.ti.sandhikaruniasugihartana

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import pnj.uas.ti.sandhikaruniasugihartana.R
import pnj.uas.ti.sandhikaruniasugihartana.model.ModelDatabase
import pnj.uas.ti.sandhikaruniasugihartana.utils.BitmapManager.base64ToBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.imageview.ShapeableImageView
import pnj.uas.ti.sandhikaruniasugihartana.databinding.ListHistoryAbsenBinding
import java.lang.String
import kotlin.Int

class HistoryAdapter(
    private val mContext: Context,
    private val modelDatabase: MutableList<ModelDatabase>,
    private val mAdapterCallback: HistoryAdapterCallback
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    // ViewBinding instance
    private lateinit var binding: ListHistoryAbsenBinding

    fun setDataAdapter(items: List<ModelDatabase>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListHistoryAbsenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDatabase[position]
        // Update the view items using the ViewBinding
        holder.bindData(data)
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewBinding references
        private val colorJenis: LinearLayout = binding.colorJenis
        private val tvStatusAbsen: TextView = binding.tvStatusAbsen
        private val tvNomor: TextView = binding.tvNomor
        private val tvJenis: TextView = binding.tvJenis
        private val tvNama: TextView = binding.tvNama
        private val tvLokasi: TextView = binding.tvLokasi
        private val tvAbsenTime: TextView = binding.tvAbsenTime
        private val cvHistory: CardView = binding.cvHistory
        private val imageProfile: ShapeableImageView = binding.imageProfile

        // Bind data to the views using ViewBinding
        fun bindData(data: ModelDatabase) {
            tvStatusAbsen.text = data.keterangan
            tvNomor.text = data.uid.toString()
            tvJenis.text = data.jenis
            tvNama.text = data.nama
            tvLokasi.text = data.lokasi
            tvAbsenTime.text = data.tanggal

            Glide.with(mContext)
                .load(base64ToBitmap(data.fotoSelfie))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_photo_camera)
                .into(imageProfile)

            when (data.jenis) {
                "Presensi Masuk" -> {
                    colorJenis.setBackgroundColor(Color.parseColor("#6A9C07"))
                }
                "Presensi Keluar" -> {
                    colorJenis.setBackgroundColor(Color.parseColor("#CD0606"))
                }
                "Izin" -> {
                    colorJenis.setBackgroundColor(Color.parseColor("#059BCD"))
                }
            }

            cvHistory.setOnClickListener {
                val modelLaundry = modelDatabase[adapterPosition]
                mAdapterCallback.onDelete(modelLaundry)
            }
        }
    }

    interface HistoryAdapterCallback {
        fun onDelete(modelDatabase: ModelDatabase?)
    }
}