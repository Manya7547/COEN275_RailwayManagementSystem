package com.Trainreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trainreservation.model.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

	Passenger findByEmailIdAndLastName(String emailId, String lastName);

}
