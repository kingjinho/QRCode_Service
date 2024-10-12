package qrcodeapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@RestController
class QRCodeRestController {

    @GetMapping(path = ["/"])
    fun hello() = ResponseEntity("Hello World", HttpStatus.OK)

    @GetMapping(path = ["/api/health"])
    fun getHealth() = ResponseEntity("OK", HttpStatus.OK)

    @GetMapping(path = ["/api/qrcode"])
    fun getQRCode(
        @RequestParam contents: String,
        @RequestParam(defaultValue = "250") size: Int,
        @RequestParam(defaultValue = "L") correction: String,
        @RequestParam(defaultValue = "png") type: String
    ): ResponseEntity<Any> {
        if (contents.isBlank()) {
            return ResponseEntity
                .badRequest()
                .body(
                    ErrorMessage(
                        error = "Contents cannot be null or blank"
                    )
                )
        }

        if (size !in 150..350) {
            return ResponseEntity
                .badRequest()
                .body(
                    ErrorMessage(
                        error = "Image size must be between 150 and 350 pixels"
                    )
                )
        }

        if (correction !in listOf("L", "M", "Q",  "H")) {
            return ResponseEntity
                .badRequest()
                .body(
                    ErrorMessage(
                        error = "Permitted error correction levels are L, M, Q, H"
                    ))
        }

        if (type !in listOf("jpeg", "png", "gif")) {
            return ResponseEntity
                .badRequest()
                .body(
                    ErrorMessage(
                        error = "Only png, jpeg and gif image types are supported"
                ))
        }

        val bufferedImage = BarcodeGenerator.create(contents, size, correction)

        ByteArrayOutputStream().use { baos ->
            ImageIO.write(bufferedImage, type, baos)
            val bytes = baos.toByteArray()
            return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("image/$type"))
                .body(bytes)
        }
    }
}
