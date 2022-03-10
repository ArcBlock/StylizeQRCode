package io.arcblock.stylizeqr

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader.TileMode.CLAMP
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import io.arcblock.stylelize_qr.StylizeQRGenerator
import io.arcblock.stylizeqr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
    val text = "https://www.arcblock.io"
    val longText = "Modern tools and resources to help you build experiences that people love, faster and easier, across every Android device."
    setContentView(binding.root)
    val bitmap = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .margin(2)
      .build()
      .generate(text)
   binding.qrNormal.setImageBitmap(bitmap)

    val gradient = GradientDrawable(GradientDrawable.Orientation.BL_TR, arrayOf(Color.BLUE, Color.RED).toIntArray())
    val bitmapGradient = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .margin(2)
      .gradient(gradient)
      .build()
      .generate(text)
    binding.qrGradient.setImageBitmap(bitmapGradient)


    val bg = ContextCompat.getDrawable(this, R.drawable.bg)?.toBitmap(150, 150, Bitmap.Config.ARGB_8888)!!
    val bitmapBG = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .background(bg)
      .margin(2)
      .build()
      .generate(longText)
    binding.qrBg.setImageBitmap(bitmapBG)

    val logo = ContextCompat.getDrawable(this, R.drawable.logo)?.toBitmap(150, 150, Bitmap.Config.RGB_565)!!
    val bitmapLogo = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .background(bg)
      .margin(2)
      .logo(logo)
      .build()
      .generate(text)
    binding.qrLogo.setImageBitmap(bitmapLogo)
  }
}