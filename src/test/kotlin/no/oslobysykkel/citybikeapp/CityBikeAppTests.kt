package no.oslobysykkel.citybikeapp

import no.oslobysykkel.citybikeapp.api.BikeStation
import no.oslobysykkel.citybikeapp.api.BikeStationController
import no.oslobysykkel.citybikeapp.api.BikeStationRepository
import no.oslobysykkel.citybikeapp.api.BikeStationStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import java.time.Instant


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //Clears cache after each test
class CityBikeAppTests(@Autowired val controller: BikeStationController) {
    @MockBean
    private lateinit var repositoryMock: BikeStationRepository

    private fun createTestData(stationId: String, shouldImmediatelyExpire: Boolean): BikeStationStatus {
        return BikeStationStatus(Instant.now(), if (shouldImmediatelyExpire) 0 else 1000,
            listOf(BikeStation(stationId, "name", 5, 5)))
    }

    @Test
    fun `controller is injected into test framework`() {
        assertThat(controller).isNotNull
    }

    @Test
    fun `BikeStationController returns mock data`() {
        val testData = createTestData("1", false)
        Mockito.`when`(repositoryMock.getBikeStationStatus()).thenReturn(testData)
        val response = controller.getBikeStationStatus()
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(testData)
    }

    @Test
    fun `BikeStationController returns cached data when not expired`() {
        val testData1 = createTestData("1", false)
        Mockito.`when`(repositoryMock.getBikeStationStatus()).thenReturn(testData1)
        controller.getBikeStationStatus()

        val testData2 = createTestData("2", false)
        Mockito.`when`(repositoryMock.getBikeStationStatus()).thenReturn(testData2)
        val response = controller.getBikeStationStatus()

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(testData1)
    }

    @Test
    fun `BikeStationController returns previously expired cached data when fetch fails`() {
        val testData = createTestData("1", true)
        Mockito.`when`(repositoryMock.getBikeStationStatus()).thenReturn(testData)
        controller.getBikeStationStatus()

        Mockito.`when`(repositoryMock.getBikeStationStatus()).thenReturn(null)
        val response = controller.getBikeStationStatus()

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo(testData)
    }
}

