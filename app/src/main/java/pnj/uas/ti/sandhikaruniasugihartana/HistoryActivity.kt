package pnj.uas.ti.sandhikaruniasugihartana

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import pnj.uas.ti.sandhikaruniasugihartana.R
import pnj.uas.ti.sandhikaruniasugihartana.model.ModelDatabase
import pnj.uas.ti.sandhikaruniasugihartana.HistoryAdapter.HistoryAdapterCallback
import pnj.uas.ti.sandhikaruniasugihartana.viewmodel.HistoryViewModel
import pnj.uas.ti.sandhikaruniasugihartana.databinding.ActivityAbsenBinding
import pnj.uas.ti.sandhikaruniasugihartana.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity(), HistoryAdapterCallback {
    var modelDatabaseList: MutableList<ModelDatabase> = ArrayList()
    lateinit var historyAdapter: HistoryAdapter
    lateinit var historyViewModel: HistoryViewModel
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setInitLayout()
        setViewModel()
    }

    private fun setInitLayout() {
        setSupportActionBar(binding.toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        binding.tvNotFound.visibility = View.GONE

        historyAdapter = HistoryAdapter(this, modelDatabaseList, this)
        binding.rvHistory.setHasFixedSize(true)
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = historyAdapter
    }

    private fun setViewModel() {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel::class.java)
        historyViewModel.dataLaporan.observe(this) { modelDatabases: List<ModelDatabase> ->
            if (modelDatabases.isEmpty()) {
                binding.tvNotFound.visibility = View.VISIBLE
                binding.rvHistory.visibility = View.GONE
            } else {
                binding.tvNotFound.visibility = View.GONE
                binding.rvHistory.visibility = View.VISIBLE
            }
            historyAdapter.setDataAdapter(modelDatabases)
        }
    }

    override fun onDelete(modelDatabase: ModelDatabase?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Hapus riwayat presensi ini?")
        alertDialogBuilder.setPositiveButton("Ya, Hapus") { dialogInterface, i ->
            val uid = modelDatabase!!.uid
            historyViewModel.deleteDataById(uid)
            Toast.makeText(this@HistoryActivity, "Data yang dipilih berhasil dihapus",
                Toast.LENGTH_SHORT).show()
        }
        alertDialogBuilder.setNegativeButton("Tidak") { dialogInterface: DialogInterface, i:
        Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}