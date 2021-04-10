package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.annotation.JsonProperty

data class StationInformationResponse(
    @JsonProperty("last_updated")
    val lastUpdated: Long,

    @JsonProperty("ttl")
    val ttl: Int,

    @JsonProperty("data")
    val data: StationInformationData?
)

data class StationInformationData(
    @JsonProperty("stations")
    val stations: List<StationInformationDataItem>
)

data class StationInformationDataItem(
    @JsonProperty("station_id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("address")
    val address: String,

    @JsonProperty("lat")
    val lat: Double,

    @JsonProperty("lon")
    val lon: Double,

    @JsonProperty("capacity")
    val capacity: Int
)