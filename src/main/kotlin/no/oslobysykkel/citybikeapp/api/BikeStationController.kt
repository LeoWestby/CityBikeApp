package no.oslobysykkel.citybikeapp.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BikeStationController(val bikeStatusService: BikeStationService) {
    @GetMapping("api/bikeStationStatus")
    fun getBikeStationStatus(): ResponseEntity<BikeStationStatus> {
        val bikeStatus = bikeStatusService.getBikeStationStatus()
            ?: return ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE)
        return ResponseEntity(bikeStatus, HttpStatus.OK)
    }
}
