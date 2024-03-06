package com.Trainreservation.service;

import java.util.List;

import com.Trainreservation.dto.ReservationDto;

public interface ManageReservationService {

	List<ReservationDto> getAllReservation(int userId);

	ReservationDto getOneReservation(int bookingId, int userId);

	List<ReservationDto> getAllReservationBasedOnEmail(String lastName, String emailId);

	ReservationDto updateReservation(ReservationDto updatedReservation, int bookingId, int userId);

	String deleteReservation(int bookingId, int userId);

	List<ReservationDto> getAllReservationBasedOnTrainNumber(String TrainNumber);

}
