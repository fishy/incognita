package com.yhsif.incognita

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.about)
    findViewById<TextView>(R.id.about_text).let { tv ->
      tv.setText(Html.fromHtml(getString(R.string.about_text), Html.FROM_HTML_MODE_LEGACY))
      tv.setMovementMethod(LinkMovementMethod.getInstance())
    }
  }
}
