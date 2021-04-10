package no.oslobysykkel.citybikeapp.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class BikeStatusService {
    private val apiIdentifier = "origo-kodeoppgave"

    fun getBikeStatus(): BikeStatus? {
        val request = HttpRequest.newBuilder()
            .uri(URI("https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json"))
            .header("Client-Identifier", apiIdentifier)
            .GET()
            .build()
        val response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200)
            return ObjectMapper().readValue(response.body())
        return null
    }
}
