package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.annotation.JsonProperty

data class StationStatusResponse(
    @JsonProperty("last_updated")
    val lastUpdated: Long,

    @JsonProperty("ttl")
    val ttl: Int,

    @JsonProperty("data")
    val data: StationStatusResponseData?
)

data class StationStatusResponseData(
    @JsonProperty("stations")
    val stations: List<StationStatusResponseDataItem>
)

data class StationStatusResponseDataItem(
    @JsonProperty("station_id")
    val id: String,

    @JsonProperty("is_installed")
    val isInstalled: Boolean,

    @JsonProperty("is_renting")
    val isRenting: Boolean,

    @JsonProperty("is_returning")
    val isReturning: Boolean,

    @JsonProperty("last_reported")
    val lastReported: Int,

    @JsonProperty("num_bikes_available")
    val availableBikeCount: Int,

    @JsonProperty("num_docks_available")
    val availableLockCount: Int
)
