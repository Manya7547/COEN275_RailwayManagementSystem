package com.Trainreservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Trainreservation.dto.TrainDetailsDto;
import com.Trainreservation.dto.TrainsDto;
import com.Trainreservation.dto.PassengerDto;
import com.Trainreservation.dto.SeatDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Seat;
import com.Trainreservation.service.BookingService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@GetMapping("/getAllTrainsAndSeats")
	public List<TrainsDto> getAllTrainsAndSeatsDetails() {
		return bookingService.getAllTrainsAndSeatsDetails();
	}

	@GetMapping("/getTrainsOnArrivalAndDeparture/{departure}/{arrival}/{departureDate}")
	public List<TrainDetailsDto> getTrainByArrivalAndDeparture(@PathVariable String departure,
			@PathVariable String arrival, @PathVariable String departureDate) {
		return bookingService.getTrainByArrivalAndDeparture(departure, arrival, departureDate);
	}

	@GetMapping("/getAllSeats/{TrainNumber}")
	public List<SeatDto> getAllSeatsFromATrain(@PathVariable String TrainNumber) {
		return bookingService.getAllSeatsFromATrain(TrainNumber);
	}

	@PostMapping("/bookTrains/{userId}")
	public PassengerDto bookTrainForAPassenger(@RequestBody TrainDetails TrainDetails, @PathVariable int userId) {
		return bookingService.bookTrainForAPassenger(TrainDetails, userId);
	}

	@PostMapping("/bookSeats/{TrainNumber}/{userId}")
	public String bookSeatForAPassenger(@RequestBody Seat seat, @PathVariable String TrainNumber,
			@PathVariable int userId) {
		return bookingService.bookSeatForAPassenger(seat, TrainNumber, userId);
	}

}
