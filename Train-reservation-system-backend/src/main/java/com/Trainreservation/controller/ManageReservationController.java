package com.Trainreservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.service.ManageReservationService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/manageBooking")
public class ManageReservationController {

	@Autowired
	ManageReservationService manageReservationService;

	@GetMapping("/viewAllReservation/{userId}")
	public List<ReservationDto> getAllReservation(@PathVariable int userId) {
		return manageReservationService.getAllReservation(userId);
	}

	@GetMapping("/viewAllReservation/{lastName}/{emailId}")
	public List<ReservationDto> getAllReservationBasedOnEmail(@PathVariable String lastName,
			@PathVariable String emailId) {
		return manageReservationService.getAllReservationBasedOnEmail(lastName, emailId);
	}
	
	@GetMapping("/viewReservationBasedOnTrainNumber/{TrainNumber}")
	public List<ReservationDto> getAllReservationBasedOnTrainNumber(@PathVariable String TrainNumber) {
		return manageReservationService.getAllReservationBasedOnTrainNumber(TrainNumber);
	}

	@GetMapping("/viewSingleReservation/{bookingId}/{userId}")
	public ReservationDto getOneReservation(@PathVariable int bookingId, @PathVariable int userId) {
		return manageReservationService.getOneReservation(bookingId, userId);
	}

	@PutMapping("/updateReservation/{bookingId}/{userId}")
	public ReservationDto updateReservation(@RequestBody ReservationDto updatedReservation, @PathVariable int bookingId,
			@PathVariable int userId) {
		return manageReservationService.updateReservation(updatedReservation, bookingId, userId);
	}
	
	@DeleteMapping("/deleteReservation/{bookingId}/{userId}")
	public String deleteReservation(@PathVariable int bookingId,
			@PathVariable int userId) {
		return manageReservationService.deleteReservation(bookingId, userId);
	}

}
