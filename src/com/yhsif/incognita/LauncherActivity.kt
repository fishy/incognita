package com.yhsif.incognita

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent

class LauncherActivity : Activity() {

  companion object {
    val ALLOWED_SCHEMES = setOf("http", "https")
  }

  override fun onResume() {
    var origText: String = ""
    var url: Uri? = null
    getIntent()?.let { intent ->
      when (intent.getAction()) {
        Intent.ACTION_VIEW -> url = intent.getData()
        Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT)?.let { text ->
          origText = text
          url = Uri.parse(text)
        }
        else -> {}
      }
    }
    if (url == null || !ALLOWED_SCHEMES.contains(url?.getScheme())) {
      if (origText != "") {
        val text = getString(R.string.toast_not_url, origText)
        val msg = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
          text
        } else {
          getString(
            R.string.toast_text_template,
            getString(R.string.app_name),
            text,
          )
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
      }
      url = Uri.parse("https://github.com/fishy/incognita/blob/main/FAQ.md")
    }

    CustomTabsIntent.Builder().setDefaultColorSchemeParams(
      CustomTabColorSchemeParams.Builder().setToolbarColor(
        R.color.primary,
      ).build(),
    ).addMenuItem(
      getString(R.string.about),
      PendingIntent.getActivity(
        this,
        0,
        Intent(this, AboutActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE,
      ),
    ).build().apply {
      intent.putExtra("com.google.android.apps.chrome.EXTRA_OPEN_NEW_INCOGNITO_TAB", true)
    }.launchUrl(this, url!!)

    finish()
    super.onResume()
  }
}
