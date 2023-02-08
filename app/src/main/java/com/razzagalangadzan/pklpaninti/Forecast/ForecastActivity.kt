package com.razzagalangadzan.pklpaninti.Forecast

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.razzagalangadzan.pklpaninti.Forecast.mvvm.ui.main.view.ForecastHomeFragment
import com.razzagalangadzan.pklpaninti.R

class ForecastActivity : AppCompatActivity() {

    private var pressedTime: Long = 0

    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(baseContext, "Ketuk lagi untuk keluar!", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor = resources.getColor(R.color.forecast_blue_start, this.theme)
        } else
            window.statusBarColor = resources.getColor(R.color.forecast_blue_start)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        replaceFragment(ForecastHomeFragment())
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, fragment)
        fragmentTransaction.commit()
    }
}