package com.Trainreservation.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Passenger;
import com.Trainreservation.model.Payment;
import com.Trainreservation.model.Reservation;
import com.Trainreservation.model.Seat;
import com.Trainreservation.repository.BookingRepository;
import com.Trainreservation.repository.PassengerRepository;
import com.Trainreservation.repository.PaymentRepository;
import com.Trainreservation.repository.ReservationRepository;
import com.Trainreservation.repository.SeatRepository;
import com.Trainreservation.service.ManageReservationService;
import com.Trainreservation.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	ManageReservationService manageReservationService;

	@Override
	public ReservationDto makePayemntAndCreateReservation(Payment payment, int userId, String TrainNumber,
			String seatNumber) {
		Seat seat = seatRepository.findSingleSeatNumber(seatNumber, TrainNumber);
		Passenger passenger = passengerRepository.findById(userId).get();
		if (payment.getTotalTravelFair() == 0) {
			payment.setTotalTravelFair(seat.getPrice());
		}

		payment.setTrainNumber(TrainNumber);
		paymentRepository.save(payment);
		passenger.getPayment().add(payment);
		passengerRepository.save(passenger);

		// create reservation
		return createReservation(TrainNumber, passenger);
	}

	private ReservationDto createReservation(String TrainNumber, Passenger passenger) {

		TrainDetails TrainDetails = bookingRepository.findByTrainNumber(TrainNumber);
		Reservation reservation = new Reservation();
		reservation.setArrival(TrainDetails.getArrival());
		reservation.setDeparture(TrainDetails.getDeparture());
		reservation.setPassenger(passenger);
		reservation.setTrain(TrainDetails);
		Reservation reservationResult = reservationRepository.save(reservation);

		return manageReservationService.getOneReservation(reservationResult.getBookingId(), passenger.getUserId());

	}

}
