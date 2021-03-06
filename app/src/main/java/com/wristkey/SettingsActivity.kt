package com.wristkey

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import android.view.View
import android.widget.*
import androidx.wear.widget.BoxInsetLayout

class SettingsActivity : WearableActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val boxinsetlayout = findViewById<BoxInsetLayout>(R.id.BoxInsetLayout)
        val settingsLabelText = findViewById<TextView>(R.id.SettingsLabel)
        val themeLabelText = findViewById<TextView>(R.id.ThemeLabel)
        val alertsLabelText = findViewById<TextView>(R.id.AlertsLabel)
        val privacyLabelText = findViewById<TextView>(R.id.privacyLabel)
        val beep = findViewById<CheckBox>(R.id.Beep)
        val vibrate = findViewById<CheckBox>(R.id.Vibrate)
        val ambientMode = findViewById<CheckBox>(R.id.AmbientMode)
        val screenLock = findViewById<CheckBox>(R.id.ScreenLock)
        val accentLabelText = findViewById<TextView>(R.id.AccentLabel)
        val numberOfItemsText = findViewById<TextView>(R.id.NumberOfItems)
        val deleteButtonText = findViewById<TextView>(R.id.DeleteAllAccountsButtonLabel)
        val deleteButton = findViewById<ImageView>(R.id.DeleteButton)
        val exportButtonText = findViewById<TextView>(R.id.ExportAllAccountsButtonLabel)
        val exportButton = findViewById<ImageView>(R.id.ExportButton)
        val backButton = findViewById<ImageButton>(R.id.AuthenticatorBackButton)
        val accentGroup = findViewById<RadioGroup>(R.id.AccentRadioGroup)
        val themeGroup = findViewById<RadioGroup>(R.id.ThemeRadioGroup)
        var currentAccent = appData.getString("accent", "4285F4")
        var currentTheme = appData.getString("theme", "000000")

        boxinsetlayout.setBackgroundColor(Color.parseColor("#"+currentTheme))
        backButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))
        deleteButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))
        ambientMode.buttonTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))
        screenLock.buttonTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))
        beep.buttonTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))
        vibrate.buttonTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))
        exportButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#"+currentAccent))

        beep.isChecked = appData.getBoolean("beep", false)
        vibrate.isChecked = appData.getBoolean("vibrate", false)
        ambientMode.isChecked = appData.getBoolean("ambient_mode", false)
        screenLock.isChecked = appData.getBoolean("screen_lock", true)

        if (currentTheme == "F7F7F7") {
            settingsLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            alertsLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            privacyLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            beep.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            vibrate.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            ambientMode.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            screenLock.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            themeLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            accentLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            deleteButtonText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            exportButtonText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
        } else {
            settingsLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#BDBDBD")))
            alertsLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            privacyLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            beep.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            vibrate.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            ambientMode.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            screenLock.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            themeLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            accentLabelText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            deleteButtonText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            exportButtonText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
        }

        if (currentAccent == "FF4141") {
            accentGroup.check(R.id.Red)
        } else if (currentAccent == "FF6D00") {
            accentGroup.check(R.id.Saffron)
        } else if (currentAccent == "FFBB00") {
            accentGroup.check(R.id.Yellow)
        } else if (currentAccent == "4285F4") {
            accentGroup.check(R.id.Blue)
        } else if (currentAccent == "009688") {
            accentGroup.check(R.id.Teal)
        } else if (currentAccent == "434343") {
            accentGroup.check(R.id.Dark)
        }

        if (currentTheme == "F7F7F7") {
            themeGroup.check(R.id.LightTheme)
        } else if (currentTheme == "192835") {
            themeGroup.check(R.id.GrayTheme)
        } else if (currentTheme == "000000") {
            themeGroup.check(R.id.DarkTheme)
        }

        numberOfItemsText.text = "${accounts.all.size} accounts"

        deleteButton.setOnClickListener {
            val intent = Intent(applicationContext, DeleteActivity::class.java)
            intent.putExtra("delete_all", true)
            startActivity(intent)
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(500)
            true
            finish()
        }

        if (accounts.all.isNotEmpty()) {
            exportButtonText.setOnClickListener {
                val intent = Intent(applicationContext, ExportActivity::class.java)
                startActivity(intent)
                val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibratorService.vibrate(50)
                true
                finish()
            }
        } else {
            exportButton.visibility = View.GONE
            exportButtonText.visibility = View.GONE
            val divider2 = findViewById<View>(R.id.divider2)
            divider2.visibility = View.GONE
        }

        accentGroup.setOnCheckedChangeListener { _, _ ->
            val selectedIndex =
                accentGroup.indexOfChild(findViewById(accentGroup.checkedRadioButtonId)).toString()
            if (selectedIndex == "0") {
                appData.edit().putString("accent", "FF4141").apply()
            } else if (selectedIndex == "1") {
                appData.edit().putString("accent", "FF6D00").apply()
            } else if (selectedIndex == "2") {
                appData.edit().putString("accent", "FFBB00").apply()
            } else if (selectedIndex == "3") {
                appData.edit().putString("accent", "4285F4").apply()
            } else if (selectedIndex == "4") {
                appData.edit().putString("accent", "009688").apply()
            } else if (selectedIndex == "5") {
                appData.edit().putString("accent", "434343").apply()
            }
        }

        themeGroup.setOnCheckedChangeListener { _, _ ->
            val selectedIndex =
                themeGroup.indexOfChild(findViewById(themeGroup.checkedRadioButtonId)).toString()
            if (selectedIndex == "0") {
                appData.edit().putString("theme", "F7F7F7").apply()
            } else if (selectedIndex == "1") {
                appData.edit().putString("theme", "192835").apply()
            } else if (selectedIndex == "2") {
                appData.edit().putString("theme", "000000").apply()
            }
            appData.edit().apply()
        }

        beep.setOnCheckedChangeListener { _, b ->
            appData.edit().putBoolean("beep", b).apply()
        }

        vibrate.setOnCheckedChangeListener { _, b ->
            appData.edit().putBoolean("vibrate", b).apply()
        }

        val lockscreen = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        if (!lockscreen.isKeyguardSecure) {
            screenLock.visibility = View.GONE
        } else {
            screenLock.visibility = View.VISIBLE
        }

        ambientMode.setOnCheckedChangeListener { _, b ->
            appData.edit().putBoolean("ambient_mode", b).apply()
        }

        screenLock.setOnCheckedChangeListener { _, b ->
            appData.edit().putBoolean("screen_lock", b).apply()
        }

        backButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(50)
            finish()
        }

    }
}