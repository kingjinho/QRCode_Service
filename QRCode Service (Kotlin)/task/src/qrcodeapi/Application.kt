package qrcodeapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.BufferedImageHttpMessageConverter

@SpringBootApplication
class QRCodeApplication {

    @Bean
    fun bufferedImageHttpMessageConverter() = BufferedImageHttpMessageConverter()

}

fun main(args: Array<String>) {
    runApplication<QRCodeApplication>(*args)
}
