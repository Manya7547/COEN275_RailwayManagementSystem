package com.Trainreservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trainreservation.model.Passenger;

@Repository
public interface AdminRepository extends JpaRepository<Passenger, Integer> {

	List<Passenger> findByAdmin(int admin);
}
