package com.Trainreservation.service;

import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.model.Payment;

public interface PaymentService {

	ReservationDto makePayemntAndCreateReservation(Payment payment, int userId, String TrainNumber,
			String seatNumber);

}
