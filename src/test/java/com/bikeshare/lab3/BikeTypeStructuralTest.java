package com.bikeshare.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;

import org.junit.jupiter.api.Test;

import com.bikeshare.model.BikeType;

public class BikeTypeStructuralTest {
  @Test
  void testElectricBikeDisplayName(){
    
    assertEquals("Electric Bike", BikeType.ELECTRIC.getDisplayName());
  }

  @Test
  void testGetCargoBikePricePerMinute(){
    assertEquals(1.20, BikeType.CARGO.getPricePerMinute());
  }

  @Test
  void testIsElectric(){

   assertTrue(BikeType.ELECTRIC.isElectric());
  }

  @Test
  void testIsNotElectric(){
    assertFalse(BikeType.CARGO.isElectric());
  }

  @Test
  void testMaxSpeedForElectric25Kmh(){
    assertEquals(25, BikeType.ELECTRIC.getMaxSpeedKmh());

  }

  @Test
  void testInvalidMaxSpeedForElectric30Kmh(){

    assertNotEquals(30, BikeType.ELECTRIC.getMaxSpeedKmh());
  }

  @Test
  void testMountainIsMediumHeavyWeightCatagory(){
    assertEquals("Medium-Heavy", BikeType.MOUNTAIN.getWeightCategory());
  }

  @Test
  void testMountainIsNotLightWeightCatagory(){
    assertNotEquals("Light", BikeType.MOUNTAIN.getWeightCategory());
  }
    
}
