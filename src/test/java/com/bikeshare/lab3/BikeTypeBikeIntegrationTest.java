package com.bikeshare.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import com.bikeshare.model.Bike;
import com.bikeshare.model.BikeType;
import com.bikeshare.model.Bike.BikeStatus;

public class BikeTypeBikeIntegrationTest {

    @Test
    void testStartRideAvailableBike() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);

        bike.startRide();
        assertEquals(Bike.BikeStatus.IN_USE, bike.getStatus());
    }

    @Test
    void testStartRideElectricBikeWithSufficientBattery() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        bike.startRide();

        assertEquals(Bike.BikeStatus.IN_USE, bike.getStatus());
        assert (bike.getBatteryLevel() >= 10);
    }

    @Test
    void testStartRideWithReservedBike() {
        Bike bike = new Bike("1", Bike.BikeType.PREMIUM);

        // sätter bike till reserved för att säkerställa att det går att starta en ride
        // med en resarverad bike
        bike.reserve();
        bike.startRide();

        assertEquals(Bike.BikeStatus.IN_USE, bike.getStatus());
    }

    @Test
    void testStartRideBikeInUse() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);
        bike.startRide();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bike.startRide();
        });

        assertEquals("Cannot start ride with bike in status: IN_USE", exception.getMessage());
    }

    @Test
    void testStartRideWithElectricBikeInsufficientBattery() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);

        // startar en ride för att få ner batteri nivån. Slutar ride efter 47km eftersom
        // detta använder 94% av batteriet.
        bike.startRide();
        bike.endRide(47);

        // Startar en ny ride med samma bike, nu är batteriet 6%, fångar exeptionmessage
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bike.startRide();
        });

        assertEquals("Electric bike battery too low to start ride", exception.getMessage());
    }

    // testar endRide
    @Test
    void endRideStandardBike() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);
        bike.startRide();

        bike.endRide(50);
        assertEquals(Bike.BikeStatus.AVAILABLE, bike.getStatus());
    }

    @Test
    void endRideElectricBike() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        bike.startRide();

        bike.endRide(30);
        assertEquals(40, bike.getBatteryLevel());
        assertEquals(Bike.BikeStatus.AVAILABLE, bike.getStatus());
    }

    @Test
    void endRideAvalibleBike() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);

        assertEquals(Bike.BikeStatus.AVAILABLE, bike.getStatus());
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bike.endRide(30);
        });

        assertEquals("Cannot end ride for bike not in use", exception.getMessage());
    }

    @Test
    void endRideNegativeDistance() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);
        bike.startRide();

        assertEquals(Bike.BikeStatus.IN_USE, bike.getStatus());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bike.endRide(-10);
        });

        assertEquals("Distance traveled cannot be negative", exception.getMessage());
    }

    // testar om bike får rätt batterinivå

    @Test
    void batteryLevelStandardBikeIsMinusOne() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);

        assertEquals(-1.0, bike.getBatteryLevel());
    }

    @Test
    void batteryLevelPremiunBikeIsMinusOne() {
        Bike bike = new Bike("1", Bike.BikeType.PREMIUM);

        assertEquals(-1.0, bike.getBatteryLevel());
    }

    @Test
    void batteryLevelElectricBikeIs100() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);

        assertEquals(100, bike.getBatteryLevel());
    }

    // testar om charge battery fungerar korrekt, endast electric bike ska ladda
    @Test
    void chargeBatteryOnElectricBikeTo80() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        // startar en ride för att få ner batteri nivån. Slutar ride efter 47km eftersom
        // detta använder 94% av batteriet.
        bike.startRide();
        bike.endRide(47);

        bike.chargeBattery(74);
        assertEquals(80, bike.getBatteryLevel());
    }

    @Test
    void chargeBatteryOnElectricBikeToHigh() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        // startar en ride för att få ner batteri nivån. Slutar ride efter 47km eftersom
        // detta använder 94% av batteriet.
        bike.startRide();
        bike.endRide(47);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bike.chargeBattery(110);
        });
        assertEquals("Charge amount must be between 0 and 100", exception.getMessage());
    }

    @Test
    void chargeBatteryOnElectricBikeTooLow() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        // startar en ride för att få ner batteri nivån. Slutar ride efter 47km eftersom
        // detta använder 94% av batteriet.
        bike.startRide();
        bike.endRide(47);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bike.chargeBattery(-20);
        });
        assertEquals("Charge amount must be between 0 and 100", exception.getMessage());
    }

    @Test
    void chargeBatteryOnStandardBikeExceptionMessage() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bike.chargeBattery(80);
        });
        assertEquals("Cannot charge non-electric bike", exception.getMessage());
    }

    // testar konstruktorn
    @Test
    void createBikeWithInvalidIdNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Bike bike = new Bike("", Bike.BikeType.ELECTRIC);
        });
        assertEquals("Bike ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void createBikeWithInvalidBikeType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Bike bike = new Bike("3", null);
        });
        assertEquals("Bike type cannot be null", exception.getMessage());
    }

    @Test
    void endRideWith1050kmNeedsMantinance() {
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);
        bike.startRide();

        bike.endRide(1050);
        assertTrue(bike.needsMaintenance());
    }

    @Test
    void endElectricRideWith4batteryNeedsMantinance() {
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        bike.startRide();

        bike.endRide(48);
        assertTrue(bike.needsMaintenance());
    }

}
