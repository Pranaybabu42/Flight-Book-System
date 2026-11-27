package com.example.flightbooking.repository;

/**
 * Lightweight projection for the airport name and city returned by country searches.
 */
public interface AirportSummaryProjection {
    String getName();

    String getCity();
}
