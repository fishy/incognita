package com.yhsif.incognita

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat

class LauncherActivity : Activity() {

  companion object {
    val ALLOWED_SCHEMES = setOf("http", "https")

    val FAQ_URL = Uri.parse("https://github.com/fishy/incognita/blob/main/FAQ.md")
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
      url = FAQ_URL
    }

    ShortcutManagerCompat.setDynamicShortcuts(
      this,
      listOf(
        ShortcutInfoCompat.Builder(this, "com.yhsif.incognita.incognita")
          .setShortLabel(getText(R.string.app_name))
          .setLongLabel(getText(R.string.app_name))
          .setIcon(IconCompat.createWithResource(this, R.mipmap.icon))
          .setCategories(setOf("com.yhsif.incognita.category.TEXT_SHARE_TARGET"))
          .setIntent(Intent(Intent.ACTION_SEND).setClass(this, LauncherActivity::class.java))
          .build(),
      ),
    )

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
