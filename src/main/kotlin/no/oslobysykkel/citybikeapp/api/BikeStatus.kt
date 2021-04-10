package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.annotation.JsonProperty

data class BikeStatus(
    @JsonProperty("last_updated")
    val lastUpdated: String,

    @JsonProperty("ttl")
    val ttl: String,

    @JsonProperty("data")
    val data: BikeStationStatusData?
)

data class BikeStationStatusData(
    @JsonProperty("stations")
    val stations: List<BikeStation>
)
