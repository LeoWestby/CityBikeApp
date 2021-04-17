package no.oslobysykkel.citybikeapp

import no.oslobysykkel.citybikeapp.api.BikeStationController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CityBikeAppTests(@Autowired val controller: BikeStationController) {
    @Test
    fun controllerIsInjectedIntoTestFramework() {
        assertThat(controller).isNotNull
    }
}

