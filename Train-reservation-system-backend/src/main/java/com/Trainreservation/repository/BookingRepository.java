package com.Trainreservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Trainreservation.model.TrainDetails;

import jakarta.transaction.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<TrainDetails, String> {
	
	List<TrainDetails> findByArrivalAndDepartureAndDepartureDate(String arrival, String departure, String departureDate);
	
	TrainDetails findByTrainNumber(String TrainNumber);
	
	@Modifying
	@Transactional
	@Query("UPDATE TrainDetails s SET s.seatAvailability = :newAvailability WHERE s.TrainNumber = :TrainNumber")
	void updateSeatAvailabilityByTrainNumber(@Param("newAvailability") int newAvailability,
			@Param("TrainNumber") String TrainNumber);
	
}
