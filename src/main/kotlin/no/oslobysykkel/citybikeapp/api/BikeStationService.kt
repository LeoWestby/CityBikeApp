package no.oslobysykkel.citybikeapp.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BikeStationService(@Autowired val bikeStationRepository: BikeStationRepository) {
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
            val updatedStatus = bikeStationRepository.getBikeStationStatus()

            if (updatedStatus == null) {
                //Something went wrong. Fall back to cached value and increment cache expiry to try again in 10 seconds
                cacheExpiry = Instant.now().plusSeconds(10)
                return cachedBikeStationStatus
            }

            cachedBikeStationStatus = updatedStatus
            cacheExpiry = updatedStatus.updatedAt.plusSeconds(updatedStatus.ttlInSeconds.toLong())
            return cachedBikeStationStatus
        }
    }
}
