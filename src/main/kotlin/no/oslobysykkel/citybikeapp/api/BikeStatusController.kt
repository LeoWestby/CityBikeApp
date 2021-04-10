package no.oslobysykkel.citybikeapp.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BikeStatusController {
    val bikeStatusService = BikeStatusService()

    @GetMapping("api/bikeStatus")
    fun index(): ResponseEntity<BikeStationStatus> {
        val bikeStatus = bikeStatusService.getBikeStatus()
            ?: return ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE)
        return ResponseEntity(bikeStatus, HttpStatus.OK)
    }
}
