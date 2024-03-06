package com.Trainreservation.service;

import com.Trainreservation.dto.PassengerDto;
import com.Trainreservation.model.Passenger;

public interface PassengerService {

	PassengerDto savePassengerDetailsToDB(Passenger passengerObj);

	PassengerDto updatePassengerDetails(Passenger passenger, int userId);

	PassengerDto getPassengerById(int userId);

}
