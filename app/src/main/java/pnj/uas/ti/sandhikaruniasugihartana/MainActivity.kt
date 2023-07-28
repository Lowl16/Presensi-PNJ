// Nama  : Sandhi Karunia Sugihartana
// Kelas : TI-4A
// NIM   : 2107411007

package pnj.uas.ti.sandhikaruniasugihartana

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import pnj.uas.ti.sandhikaruniasugihartana.R
import pnj.uas.ti.sandhikaruniasugihartana.utils.SessionLogin
import pnj.uas.ti.sandhikaruniasugihartana.AbsenActivity
import pnj.uas.ti.sandhikaruniasugihartana.HistoryActivity
import pnj.uas.ti.sandhikaruniasugihartana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var strTitle: String
    lateinit var session: SessionLogin
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setInitLayout()
    }

    private fun setInitLayout() {
        session = SessionLogin(this)
        session.checkLogin()

        binding.cvAbsenMasuk.setOnClickListener {
            strTitle = "Presensi Masuk"
            val intent = Intent(this@MainActivity, AbsenActivity::class.java)
            intent.putExtra(AbsenActivity.DATA_TITLE, strTitle)
            startActivity(intent)
        }

        binding.cvAbsenKeluar.setOnClickListener {
            strTitle = "Presensi Keluar"
            val intent = Intent(this@MainActivity, AbsenActivity::class.java)
            intent.putExtra(AbsenActivity.DATA_TITLE, strTitle)
            startActivity(intent)
        }

        binding.cvPerizinan.setOnClickListener {
            strTitle = "Izin"
            val intent = Intent(this@MainActivity, AbsenActivity::class.java)
            intent.putExtra(AbsenActivity.DATA_TITLE, strTitle)
            startActivity(intent)
        }

        binding.cvHistory.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setMessage("Yakin Anda ingin Logout?")
            builder.setCancelable(true)
            builder.setNegativeButton("Tidak") { dialog, which -> dialog.cancel() }
            builder.setPositiveButton("Ya") { dialog, which ->
                session.logoutUser()
                finishAffinity()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}