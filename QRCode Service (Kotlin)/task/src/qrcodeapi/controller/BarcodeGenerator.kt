package qrcodeapi.controller

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage

object BarcodeGenerator {

    fun create(contents: String, size: Int, correction: String): BufferedImage {
        val qrWriter = QRCodeWriter()
        val hint = mapOf(EncodeHintType.ERROR_CORRECTION to correction)
        val bitMatrix = qrWriter.encode(contents, BarcodeFormat.QR_CODE, size, size, hint)
        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }
}