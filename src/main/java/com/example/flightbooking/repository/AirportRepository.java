package com.example.flightbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.flightbooking.model.Airport;
/*
 * Airport Repository
 */
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>{

	@Query("SELECT COUNT(a) FROM Airport a WHERE LOWER(TRIM(a.country)) = LOWER(:country)")
	long countByNormalizedCountry(@Param("country") String country);

	@Query("SELECT a.name AS name, a.city AS city FROM Airport a "
			+ "WHERE LOWER(TRIM(a.country)) = LOWER(:country) ORDER BY a.name, a.city")
	List<AirportSummaryProjection> findSummariesByNormalizedCountry(@Param("country") String country);
}
