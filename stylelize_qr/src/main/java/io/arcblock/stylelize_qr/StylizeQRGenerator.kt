package io.arcblock.stylelize_qr

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.Cap.ROUND
import android.graphics.Paint.Style.FILL
import android.graphics.Paint.Style.STROKE
import android.graphics.drawable.GradientDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.get
import androidx.core.graphics.scale
import com.google.zxing.EncodeHintType
import com.google.zxing.EncodeHintType.CHARACTER_SET
import com.google.zxing.EncodeHintType.ERROR_CORRECTION
import com.google.zxing.EncodeHintType.MARGIN
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H
import com.google.zxing.qrcode.encoder.ByteMatrix
import com.google.zxing.qrcode.encoder.Encoder
import java.util.Hashtable

class StylizeQRGenerator(
  private var dotStyle: Boolean = true,
  private var gradient: GradientDrawable? = null,
  private var logo: Bitmap? = null,
  private var backgroud: Bitmap? = null,
  private var margin: Int = 0,
) {
  // dot style dot diameter
  private val DOT_SIZE = 10

  @Throws
  fun generate(text: String): Bitmap {
    val hints = Hashtable<EncodeHintType, Any?>()
    hints[CHARACTER_SET] = "utf-8"
    hints[ERROR_CORRECTION] = H
    hints[MARGIN] = 0
    val qrCode = Encoder.encode(text, H, hints)
    return drawCanvas(qrCode.matrix)
  }

  private fun drawCanvas(src: ByteMatrix): Bitmap {
    // + 1 for edge dot draw
    val srcWidth: Int = (src.width + margin * 2 + 1) * DOT_SIZE
    val srcHeight: Int = (src.width + margin * 2 + 1) * DOT_SIZE
    var bitmap = Bitmap.createBitmap(srcWidth, srcHeight, ARGB_8888)
    try {
      val paintStroke = Paint()
      paintStroke.style = STROKE
      paintStroke.strokeCap = ROUND
      paintStroke.strokeWidth = DOT_SIZE.toFloat()
      val paintSolid = Paint()
      paintSolid.color = Color.BLACK
      paintSolid.style = FILL
      val paintWhite = Paint()
      paintWhite.color = Color.WHITE
      paintWhite.style = FILL
      val canvas = Canvas(bitmap)
      canvas.drawRoundRect(0f, 0f, srcWidth.toFloat(), srcHeight.toFloat(), 30f, 30f, paintWhite)

      canvas.drawRoundRect(
        ((1 + margin) * DOT_SIZE).toFloat(),
        (10 + margin * DOT_SIZE).toFloat(),
        (70 + margin * DOT_SIZE).toFloat(),
        (70 + margin * DOT_SIZE).toFloat(),
        20f,
        20f,
        paintStroke
      )
      canvas.drawCircle(
        (40 + margin * DOT_SIZE).toFloat(),
        (40 + margin * DOT_SIZE).toFloat(),
        15f,
        paintSolid
      )
      canvas.drawRoundRect(
        ((1 + margin) * DOT_SIZE).toFloat(),
        (srcHeight - (7 + margin) * DOT_SIZE).toFloat(),
        ((7 + margin) * 10).toFloat(),
        (srcHeight - (1 + margin) * DOT_SIZE).toFloat(),
        20f,
        20f,
        paintStroke
      )
      canvas.drawCircle(
        ((4 + margin) * DOT_SIZE).toFloat(),
        (srcHeight - (4 + margin) * DOT_SIZE).toFloat(),
        15f,
        paintSolid
      )
      canvas.drawRoundRect(
        (srcWidth - (7 + margin) * DOT_SIZE).toFloat(),
        ((1 + margin) * DOT_SIZE).toFloat(),
        (srcWidth - (1 + margin) * DOT_SIZE).toFloat(),
        ((7 + margin) * DOT_SIZE).toFloat(),
        20f,
        20f,
        paintStroke
      )
      canvas.drawCircle(
        (srcWidth - (4 + margin) * DOT_SIZE).toFloat(),
        ((4 + margin) * DOT_SIZE).toFloat(),
        15f,
        paintSolid
      )




      val bgColors = if (gradient != null) {
        gradient?.toBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
      } else if (backgroud != null) {
        backgroud?.scale(src.width, src.height, false)
      }else null
      for (i in 0 until src.width) {
        for (j in 0 until src.height) {
          // do not to draw positioning point
          if (j < 7 && i < 7 || i < 7 && j > src.height - 8 || (j < 7
                && i > src.height - 8)
          ) {
            continue
          }
          if (src.get(i, j).toInt() == 1) {
            if (bgColors != null){
              val hsv = FloatArray(3)
              Color.colorToHSV(bgColors[i, j], hsv)
              hsv[2] = if (hsv[2] > 0.6) hsv[2]-0.3f else hsv[2]
              paintSolid.color = Color.HSVToColor(hsv)
            }
            if (dotStyle){
            canvas.drawCircle(
              ((i + 1 + margin) * DOT_SIZE).toFloat(),
              ((j + 1 + margin) * DOT_SIZE).toFloat(),
              DOT_SIZE.toFloat() / 2,
              paintSolid
            )}else {
              canvas.drawRect(i*DOT_SIZE.toFloat(), j*DOT_SIZE.toFloat(), (i+1)*DOT_SIZE.toFloat(), (j+1)*DOT_SIZE.toFloat(), paintSolid)
            }
          }
        }
      }
      if (logo != null) {
        val logoWidth = logo!!.width
        val logoHeight = logo!!.height
        val scaleFactor = srcWidth * 1.0f / 5 / logoWidth
        canvas.scale(scaleFactor, scaleFactor, (srcWidth / 2).toFloat(), (srcHeight / 2).toFloat())
        canvas.drawBitmap(
          logo!!,
          ((srcWidth - logoWidth) / 2).toFloat(),
          ((srcHeight - logoHeight) / 2).toFloat(),
          null
        )
      }
      canvas.save()
      canvas.restore()
    } catch (e: Exception) {
      e.printStackTrace()
      bitmap = null
    }
    return bitmap!!
  }

  data class Builder(
    private var dotStyle: Boolean = true,
    private var gradient: GradientDrawable? = null,
    private var logo: Bitmap? = null,
    private var backgroud: Bitmap? = null,
    private var margin: Int = 0
  ) {
    fun dotStyle(enable: Boolean) = apply { dotStyle = enable }
    fun gradient(gradient: GradientDrawable) = apply { this.gradient = gradient }
    fun logo(logo: Bitmap) = apply { this.logo = logo }
    fun background(bg: Bitmap) = apply { this.backgroud = bg }
    fun margin(margin: Int) = apply { this.margin = margin }
    fun build() = StylizeQRGenerator(dotStyle, gradient, logo, backgroud, margin)
  }
}