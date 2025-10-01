package com.bikeshare.lab3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;

import org.junit.jupiter.api.Test;

import com.bikeshare.model.BikeType;

public class BikeTypeStructuralTest {
  // testar alla displayname stämmer överrens
  @Test
  void testStandardBikeDisplayName() {

    assertEquals("Standard Bike", BikeType.STANDARD.getDisplayName());
  }

  @Test
  void testElectricBikeDisplayName() {

    assertEquals("Electric Bike", BikeType.ELECTRIC.getDisplayName());
  }

  @Test
  void testMountainBikeDisplayName() {

    assertEquals("Mountain Bike", BikeType.MOUNTAIN.getDisplayName());
  }

  @Test
  void testCargoBikeDisplayName() {

    assertEquals("Cargo Bike", BikeType.CARGO.getDisplayName());
  }

  // testar alla price per minute
  @Test
  void testGetStandardBikePricePerMinute() {
    assertEquals(0.50, BikeType.STANDARD.getPricePerMinute());
  }

  @Test
  void testGetElectricBikePricePerMinute() {
    assertEquals(1.00, BikeType.ELECTRIC.getPricePerMinute());
  }

  @Test
  void testGetMountainBikePricePerMinute() {
    assertEquals(0.70, BikeType.MOUNTAIN.getPricePerMinute());
  }

  @Test
  void testGetCargoBikePricePerMinute() {
    assertEquals(1.20, BikeType.CARGO.getPricePerMinute());
  }

  // bara electric är IsElectric==true
  @Test
  void testIsElectric() {

    assertTrue(BikeType.ELECTRIC.isElectric());
  }

  @Test
  void testCargoIsNotElectric() {
    assertFalse(BikeType.CARGO.isElectric());
  }

  // testar alla maxspeed
  @Test
  void testMaxSpeedForStandard25Kmh() {
    assertEquals(25, BikeType.STANDARD.getMaxSpeedKmh());

  }

  @Test
  void testMaxSpeedForElectric25Kmh() {
    assertEquals(25, BikeType.ELECTRIC.getMaxSpeedKmh());

  }

  @Test
  void testMaxSpeedForMountain30Kmh() {
    assertEquals(30, BikeType.MOUNTAIN.getMaxSpeedKmh());

  }

  @Test
  void testMaxSpeedForCargo25Kmh() {
    assertEquals(20, BikeType.CARGO.getMaxSpeedKmh());

  }

  @Test
  void testInvalidMaxSpeedForElectric30Kmh() {

    assertNotEquals(30, BikeType.ELECTRIC.getMaxSpeedKmh());
  }

  @Test
  void testAllBikeHaveCorrectyWeightCatagory() {
    assertEquals("Light", BikeType.STANDARD.getWeightCategory());
    assertEquals("Heavy", BikeType.ELECTRIC.getWeightCategory());
    assertEquals("Medium-Heavy", BikeType.MOUNTAIN.getWeightCategory());
    assertEquals("Very Heavy", BikeType.CARGO.getWeightCategory());
  }

  @Test
  void testMountainIsNotLightWeightCatagory() {
    assertNotEquals("Light", BikeType.MOUNTAIN.getWeightCategory());
  }

}
