package com.Trainreservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Trainreservation.model.Passenger;
import com.Trainreservation.model.Reservation;
import com.Trainreservation.model.TrainDetails;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	@Query("SELECT r FROM Reservation r WHERE r.passenger.userId = :userId")
	List<Reservation> findReservationByPassengerUserId(@Param("userId") int userId);

	Reservation findByBookingIdAndPassenger(int bookingId, Passenger passenger);
	
	List<Reservation> findByTrain(TrainDetails Train);
	
	void deleteByBookingId(int bookingId);

}
