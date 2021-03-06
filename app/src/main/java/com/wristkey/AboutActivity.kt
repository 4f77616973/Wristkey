package com.wristkey


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.wear.widget.BoxInsetLayout
import com.google.android.wearable.intent.RemoteIntent
import java.text.SimpleDateFormat
import java.util.*


class AboutActivity : WearableActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val boxinsetlayout = findViewById<BoxInsetLayout>(R.id.BoxInsetLayout)
        val doneButton = findViewById<ImageView>(R.id.DoneButton)
        val appNameText = findViewById<TextView>(R.id.AppName)
        val copyrightText = findViewById<TextView>(R.id.Copyright)
        val versionText = findViewById<TextView>(R.id.Version)
        val descriptionText = findViewById<TextView>(R.id.AuthenticatorDescription)
        val currentTheme = appData.getString("theme", "000000")
        boxinsetlayout.setBackgroundColor(Color.parseColor("#$currentTheme"))
        val currentAccent = appData.getString("accent", "4285F4")
        doneButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#$currentAccent"))
        val urlLink = findViewById<TextView>(R.id.SourceCode)
        urlLink.setTextColor(ColorStateList.valueOf(Color.parseColor("#$currentAccent")))
        if (currentTheme == "F7F7F7") {
            appNameText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            copyrightText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            versionText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            descriptionText.setTextColor(ColorStateList.valueOf(Color.parseColor("#000000")))
            urlLink.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#000000"))
        } else {
            appNameText.setTextColor(ColorStateList.valueOf(Color.parseColor("#BDBDBD")))
            copyrightText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            versionText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            descriptionText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
            urlLink.compoundDrawableTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
        }

        var year = Calendar.getInstance().get(Calendar.YEAR).toString()
        if (year == "1970") year = "2021"
        copyrightText.text = "${copyrightText.text} $year"
        versionText.text = "v${BuildConfig.VERSION_NAME}"
        val uri: String = getString(R.string.about_url)

        urlLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.parse(uri))
            RemoteIntent.startRemoteActivity(this, intent, null)
            val toast = Toast.makeText(this, "URL opened\non phone", Toast.LENGTH_SHORT)
            toast.show()
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(browserIntent)
                val toast2 = Toast.makeText(this, "URL opened\nin browser", Toast.LENGTH_SHORT)
                toast2.show()
            } catch (ex: Exception) { }
        }

        doneButton.setOnClickListener {
            finish()
            val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibratorService.vibrate(50)
            finish()
        }
    }
}
