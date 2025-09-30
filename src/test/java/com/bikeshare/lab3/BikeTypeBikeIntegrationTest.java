package com.bikeshare.lab3;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.bikeshare.model.Bike;
import com.bikeshare.model.BikeType;
import com.bikeshare.model.Bike.BikeStatus;

public class BikeTypeBikeIntegrationTest {

    @Test
    void testStartRideAvailableBike(){
        Bike bike = new Bike("1", Bike.BikeType.STANDARD);

        bike.startRide();

        assertEquals(Bike.BikeStatus.IN_USE, bike.getStatus());

    }
    @Test
    void testStartRideElectricBikeWithSufficientBattery(){
        Bike bike = new Bike("1", Bike.BikeType.ELECTRIC);
        bike.startRide();

        assertEquals(Bike.BikeStatus.IN_USE, bike.getStatus());
        assert(bike.getBatteryLevel()>=10);


    }
    @Test
    void testStartRideBikeInUse(){
        Bike bike = new Bike("1",Bike.BikeType.STANDARD);
        bike.startRide();
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            bike.startRide();
        });

        
        assertEquals("Cannot start ride with bike in status: IN_USE", exception.getMessage());

        
    }

    
}
