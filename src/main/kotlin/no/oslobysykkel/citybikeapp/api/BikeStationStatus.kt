package no.oslobysykkel.citybikeapp.api

import java.time.Instant

data class BikeStationStatus (
    val updatedAt: Instant,
    val ttlInSeconds: Int,
    val stations: List<BikeStation>
)

data class BikeStation (
    val id: String,
    val name: String,
    val availableBikeCount: Int,
    val availableLockCount: Int
)