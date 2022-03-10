# StylizeQRCode
generate stylized QR code based on ZXing

## Usage

### Basic Dot Style QRCode

```kotlin
    val bitmap = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .margin(2)
      .build()
      .generate(text)
   binding.qrNormal.setImageBitmap(bitmap)
```
<img width="247" alt="image" src="https://user-images.githubusercontent.com/4629442/157608496-315116c2-dc14-4efa-81af-5543251d3a59.png">

### Gradient Style
```kotlin
    val gradient = GradientDrawable(GradientDrawable.Orientation.BL_TR, arrayOf(Color.BLUE, Color.RED).toIntArray())
    val bitmapGradient = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .margin(2)
      .gradient(gradient)
      .build()
      .generate(text)
    binding.qrGradient.setImageBitmap(bitmapGradient)
```

<img width="246" alt="image" src="https://user-images.githubusercontent.com/4629442/157608671-344ac29d-7f99-4f64-874b-0d9a2e15938a.png">


### Background Style
```kotlin
    val bg = ContextCompat.getDrawable(this, R.drawable.bg)?.toBitmap(150, 150, Bitmap.Config.ARGB_8888)!!
    val bitmapBG = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .background(bg)
      .margin(2)
      .build()
      .generate(longText)
    binding.qrBg.setImageBitmap(bitmapBG)
```
<img width="231" alt="image" src="https://user-images.githubusercontent.com/4629442/157608806-c1caa4ab-1dc2-498f-8ff4-0d44832bbef5.png">

### With Logo 

```kotlin
    val logo = ContextCompat.getDrawable(this, R.drawable.logo)?.toBitmap(150, 150, Bitmap.Config.RGB_565)!!
    val bitmapLogo = StylizeQRGenerator.Builder()
      .dotStyle(true)
      .background(bg)
      .margin(2)
      .logo(logo)
      .build()
      .generate(text)
    binding.qrLogo.setImageBitmap(bitmapLogo)
```
<img width="224" alt="image" src="https://user-images.githubusercontent.com/4629442/157608865-79a3048e-910b-400d-adac-056b28af7761.png">












