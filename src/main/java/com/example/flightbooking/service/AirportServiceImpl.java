package com.example.flightbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.flightbooking.dto.AirportCountryCountDto;
import com.example.flightbooking.dto.AirportDto;
import com.example.flightbooking.dto.AirportSummaryDto;
import com.example.flightbooking.exceptions.InvalidRequestException;
import com.example.flightbooking.exceptions.ResourceNotFoundException;
import com.example.flightbooking.mapper.AirportMapper;
import com.example.flightbooking.model.Airport;
import com.example.flightbooking.repository.AirportRepository;

/*
 * Airport Service Implementation
 */
@Service
public class AirportServiceImpl implements AirportService {
	@Autowired
	private AirportRepository airportRepository;
	
	@Autowired
	private AirportMapper airportMapper;
	
	@Transactional
	public AirportDto create(AirportDto airportDto) {
		Airport airport = airportMapper.toEntity(airportDto);
		Airport saved = airportRepository.save(airport);
		return airportMapper.toDto(saved);
	}
	public AirportDto get(Long id) throws ResourceNotFoundException {
		if (id == null) {
			return null;
		}
		return airportRepository.findById(id).map(airportMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Airport not found"));
	}

	@Override
	@Transactional(readOnly = true)
	public AirportCountryCountDto countByCountry(String country) {
		if (country == null || country.isBlank()) {
			throw new InvalidRequestException("country must not be blank");
		}

		String trimmedCountry = country.strip();
		long totalCount = airportRepository.countByNormalizedCountry(trimmedCountry);
		var airports = airportRepository.findSummariesByNormalizedCountry(trimmedCountry)
				.stream()
				.map(airport -> new AirportSummaryDto(airport.getName(), airport.getCity()))
				.toList();

		return new AirportCountryCountDto(trimmedCountry, airports, totalCount);
	}
}