package no.oslobysykkel.citybikeapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CityBikeAppApplication

fun main(args: Array<String>) {
    runApplication<CityBikeAppApplication>(*args)
}
