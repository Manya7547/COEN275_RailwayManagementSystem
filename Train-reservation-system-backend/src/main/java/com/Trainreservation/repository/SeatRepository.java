package com.Trainreservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Seat;
import com.Trainreservation.model.Passenger;


@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

	List<Seat> findByTrain(TrainDetails Train);

	List<Seat> findBySeatNumber(String seatNumber);
	
	List<Seat> findByPassengerAndTrain(Passenger passenger, TrainDetails Train);
	
	@Query("SELECT s FROM Seat s WHERE s.seatNumber = :seatNumber and s.Train.TrainNumber = :TrainNumber")
	Seat findSingleSeatNumber(@Param("seatNumber") String seatNumber,
			@Param("TrainNumber") String TrainNumber);

}
