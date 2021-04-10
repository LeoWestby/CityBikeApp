package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.annotation.JsonProperty

data class BikeStation(
    @JsonProperty("station_id")
    val id: String,

    @JsonProperty("is_installed")
    val isInstalled: Boolean,

    @JsonProperty("is_renting")
    val isRenting: Boolean,

    @JsonProperty("is_returning")
    val isReturning: Boolean,

    @JsonProperty("last_reported")
    val lastReported: Number,

    @JsonProperty("num_bikes_available")
    val availableBikeCount: Number,

    @JsonProperty("num_docks_available")
    val availableDockCount: Number
)
