package com.example.flightbooking.dto;

import java.util.List;

public record AirportCountryCountDto(String country, List<AirportSummaryDto> airports, long totalCount) {
}
