package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Instant

class BikeStationService {
    private val apiIdentifier = "origo-kodeoppgave"
    private val jsonParser = ObjectMapper()
    @Volatile private var cachedBikeStationStatus: BikeStationStatus? = null
    @Volatile private var cacheExpiry: Instant = Instant.now()

    /**
     * Receives the most recent bike station status from the external APIs.
     * Response values are cached until the expiry time specified in the response (TTL).
     * If the external APIs are unavailable, the most recently (expired) cached bike station status is returned.
     * If no cache exists, NULL is returned.
     */
    @Synchronized
    fun getBikeStationStatus(): BikeStationStatus? {
        if (cacheExpiry > Instant.now())
            return cachedBikeStationStatus
        else {
            val stationInformationResponse = fetchJson("https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json")
            val stationStatusResponse = fetchJson("https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json")

            if (stationInformationResponse.statusCode() == 200 && stationStatusResponse.statusCode() == 200) {

                val stationInformation = jsonParser.readValue<StationInformationResponse>(stationInformationResponse.body())
                val stationStatus = jsonParser.readValue<StationStatusResponse>(stationStatusResponse.body())
                val mappedStatus = BikeStationStatus(
                    Instant.ofEpochSecond(stationInformation.lastUpdated),
                    stationInformation.ttl,
                    mapBikeStations(stationInformation, stationStatus)
                )
                cachedBikeStationStatus = mappedStatus
                cacheExpiry = mappedStatus.updatedAt.plusSeconds(mappedStatus.ttlInSeconds.toLong())
                return cachedBikeStationStatus
            }
            else {
                //Something went wrong. Fall back to cached value and increment cache expiry to try again in 10 seconds
                cacheExpiry = Instant.now().plusSeconds(10)
                return cachedBikeStationStatus
            }
        }
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
