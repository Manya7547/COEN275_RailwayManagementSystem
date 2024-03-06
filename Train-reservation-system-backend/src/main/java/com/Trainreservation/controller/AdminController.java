package com.Trainreservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Trainreservation.dto.TrainsDto;
import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.service.AdminService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/adminControl")
public class AdminController {

	@Autowired
	private AdminService adminservice;

	@PostMapping("/saveTrain/{admin}")
	public String saveTrainDetails(@RequestBody TrainDetails Trainobj, @PathVariable int admin) {
		// Passenger passenger = adminservice.getPassengerAdmin(admin);
		return adminservice.saveTrainDetailsToDB(Trainobj, admin);
	}

	@GetMapping("/getAllTrains/{admin}")
	public List<TrainsDto> getAllTrainsDetails(@PathVariable int admin) {
		return adminservice.getAllTrainsDetails(admin);
	}

	@PutMapping("/updateTrain/{admin}/{TrainNumber}")
	public String updateTrainsDetailsAsAdmin(@RequestBody TrainDetails Trainobj, @PathVariable int admin,
			@PathVariable String TrainNumber) {
		return adminservice.updateTrainsDetailsAsAdminToDB(Trainobj, admin, TrainNumber);
	}

	@DeleteMapping("/deleteTrain/{admin}/{TrainNumber}")
	public String DeleteTrainDetails(@RequestBody TrainDetails Trainobj, @PathVariable int admin,
			@PathVariable String TrainNumber) {
		return adminservice.deleteTrainFromDB(Trainobj, admin, TrainNumber);
	}

	@GetMapping("/viewAllReservation/{TrainNumber}")
	public List<ReservationDto> getAllReservation(@PathVariable String TrainNumber) {
		return adminservice.getAllReservation(TrainNumber);
	}

}
