package com.Trainreservation.service;

import java.util.List;

import com.Trainreservation.dto.TrainDetailsDto;
import com.Trainreservation.dto.TrainsDto;
import com.Trainreservation.dto.PassengerDto;
import com.Trainreservation.dto.SeatDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Seat;

public interface BookingService {

	List<TrainsDto> getAllTrainsAndSeatsDetails();

	List<TrainDetailsDto> getTrainByArrivalAndDeparture(String departure, String arrival, String departureDate);

	List<SeatDto> getAllSeatsFromATrain(String TrainNumber);

	PassengerDto bookTrainForAPassenger(TrainDetails TrainDetails, int userId);
	
	String bookSeatForAPassenger(Seat seat, String TrainNumber, int userId);

}
