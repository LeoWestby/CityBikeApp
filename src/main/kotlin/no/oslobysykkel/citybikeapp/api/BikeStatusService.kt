package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Instant

class BikeStatusService {
    private val apiIdentifier = "origo-kodeoppgave"

    fun getBikeStatus(): BikeStationStatus? {
        val stationInformationResponse = fetchJson("https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json")
        val stationStatusResponse = fetchJson("https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json")

        if (stationInformationResponse.statusCode() == 200 && stationStatusResponse.statusCode() == 200) {
            val stationInformation = ObjectMapper().readValue<StationInformationResponse>(stationInformationResponse.body())
            val stationStatus = ObjectMapper().readValue<StationStatusResponse>(stationStatusResponse.body())
            return BikeStationStatus(
                Instant.ofEpochSecond(stationInformation.lastUpdated),
                stationInformation.ttl,
                mapBikeStations(stationInformation, stationStatus)
            )
        }
        return null
    }

    private fun fetchJson(url: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .header("Client-Identifier", apiIdentifier)
            .GET()
            .build()
        return HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())
    }

    private fun mapBikeStations(stationInformation: StationInformationResponse,
                                     stationStatus: StationStatusResponse): List<BikeStation>
    {
        fun getStationName(id: String): String {
            return stationInformation.data!!.stations.first { it.id == id }.name
        }
        return stationStatus.data!!.stations.map {
            BikeStation(
                it.id,
                getStationName(it.id),
                it.availableBikeCount,
                it.availableLockCount
            )
        }
    }
}
