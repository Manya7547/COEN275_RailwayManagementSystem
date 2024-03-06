package com.Trainreservation.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Trainreservation.dto.TrainDetailsDto;
import com.Trainreservation.dto.PassengerDto;
import com.Trainreservation.dto.PaymentDto;
import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.dto.SeatDto;
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

@Service
public class ManageReservationServiceImpl implements ManageReservationService {

	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	PaymentRepository paymentRepository;

	public List<ReservationDto> mapReservationToReservationDto(List<Reservation> reservation) {

		List<ReservationDto> reservationDtos = new ArrayList<ReservationDto>();
		for (Reservation retrievedDBReservation : reservation) {
			ReservationDto reservationdto = new ReservationDto();
			reservationdto.setArrival(retrievedDBReservation.getArrival());
			reservationdto.setDeparture(retrievedDBReservation.getDeparture());
			reservationdto.setBookingId(retrievedDBReservation.getBookingId());

			Passenger passenger = retrievedDBReservation.getPassenger();

			PassengerDto passengerDto = new PassengerDto();
			passengerDto.setAdmin(passenger.getAdmin());
			passengerDto.setEmailId(passenger.getEmailId());
			passengerDto.setFirstName(passenger.getFirstName());
			passengerDto.setLastName(passenger.getLastName());
			passengerDto.setUserId(passenger.getUserId());

			List<TrainDetails> TrainDetails = passenger.getTrainDetails();
			List<TrainDetailsDto> TrainDetailsDtos = new ArrayList<TrainDetailsDto>();

			List<Payment> payments = passenger.getPayment();
			List<PaymentDto> paymentDtos = new ArrayList<PaymentDto>();

			for (TrainDetails TrainDetail : TrainDetails) {
				if (TrainDetail.getTrainNumber().equals(retrievedDBReservation.getTrain().getTrainNumber())) {
					TrainDetailsDto TrainDetailsDto = new TrainDetailsDto();
					TrainDetailsDto.setArrival(TrainDetail.getArrival());
					TrainDetailsDto.setArrivalDate(TrainDetail.getArrivalDate());
					TrainDetailsDto.setArrivalTime(TrainDetail.getArrivalTime());
					TrainDetailsDto.setDeparture(TrainDetail.getDeparture());
					TrainDetailsDto.setDepartureDate(TrainDetail.getDepartureDate());
					TrainDetailsDto.setDepartureTime(TrainDetail.getDepartureTime());
					TrainDetailsDto.setTrainNumber(TrainDetail.getTrainNumber());
					TrainDetailsDto.setSeatAvailability(TrainDetail.getSeatAvailability());
					reservationdto.setTrainNumber(TrainDetail.getTrainNumber());

					List<Seat> seats = TrainDetail.getSeats();
					SeatDto seatDto = new SeatDto();
					for (Seat seat : seats) {
						if (seat.getPassenger() != null && seat.getPassenger().getUserId() == passenger.getUserId()) {
							seatDto.setBooked(seat.isBooked());
							seatDto.setPrice(seat.getPrice());
							seatDto.setSeatClass(seat.getSeatClass());
							seatDto.setSeatNumber(seat.getSeatNumber());
							seatDto.setPassengerId(passenger.getUserId());
						}
					}

					TrainDetailsDto.setSeat(seatDto);
					TrainDetailsDtos.add(TrainDetailsDto);

					for (Payment payment : payments) {
						if (payment.getTrainNumber().equals(retrievedDBReservation.getTrain().getTrainNumber())) {
							PaymentDto paymentDto = new PaymentDto();
							paymentDto.setPaymentId(payment.getPaymentId());
							paymentDto.setTotalTravelFair(payment.getTotalTravelFair());
							paymentDto.setTrainNumber(TrainDetail.getTrainNumber());

							paymentDtos.add(paymentDto);
						}
					}
				}
			}

			passengerDto.setTrainDetails(TrainDetailsDtos);
			passengerDto.setPayment(paymentDtos);
			reservationdto.setPassenger(passengerDto);
			reservationDtos.add(reservationdto);
		}

		return reservationDtos;

	}

	@Override
	public List<ReservationDto> getAllReservation(int userId) {
		List<Reservation> reservation = reservationRepository.findReservationByPassengerUserId(userId);
		if (reservation != null)
			return mapReservationToReservationDto(reservation);
		return null;
	}

	@Override
	public List<ReservationDto> getAllReservationBasedOnEmail(String lastName, String emailId) {
		Passenger passenger = passengerRepository.findByEmailIdAndLastName(emailId, lastName);
		if (passenger != null)
			return getAllReservation(passenger.getUserId());
		return null;
	}

	@Override
	public List<ReservationDto> getAllReservationBasedOnTrainNumber(String TrainNumber) {

		TrainDetails TrainDetails = bookingRepository.findByTrainNumber(TrainNumber);
		List<Reservation> reservation = reservationRepository.findByTrain(TrainDetails);
		if (reservation != null)
			return mapReservationToReservationDto(reservation);
		return null;
	}

	@Override
	public ReservationDto getOneReservation(int bookingId, int userId) {
		List<ReservationDto> retrievedReservations = getAllReservation(userId);

		for (ReservationDto rdto : retrievedReservations) {
			if (rdto.getBookingId() == bookingId) {
				return rdto;
			}
		}
		return null;
	}

	@Transactional
	@Override
	public ReservationDto updateReservation(ReservationDto updatedReservation, int bookingId, int userId) {

		Passenger existingPassenger = passengerRepository.findById(userId).get();
		Reservation existingReservation = reservationRepository.findByBookingIdAndPassenger(bookingId,
				existingPassenger);
		PassengerDto updatedPassenger = updatedReservation.getPassenger();

		TrainDetailsDto updatedTrainDetails = updatedReservation.getPassenger().getTrainDetails().get(0);
		TrainDetails existingTrainDetails = bookingRepository
				.findByTrainNumber(updatedTrainDetails.getTrainNumber());

		// remove the already booked passenger seat
		List<Seat> bookedSeats = seatRepository.findByPassengerAndTrain(existingPassenger, existingTrainDetails);
		for (Seat bookedSeat : bookedSeats) {
			bookedSeat.setBooked(false);
			bookedSeat.setPassenger(null);
			seatRepository.save(bookedSeat);
		}

		// Update passenger details
		existingPassenger.setFirstName(updatedPassenger.getFirstName());
		existingPassenger.setLastName(updatedPassenger.getLastName());
		existingPassenger.setAdmin(updatedPassenger.getAdmin());
		existingPassenger.setEmailId(updatedPassenger.getEmailId());
		existingPassenger.setUserId(updatedPassenger.getUserId());

		// Update seat details
		SeatDto updatedSeat = updatedTrainDetails.getSeat();
		Seat existingSeat = seatRepository.findSingleSeatNumber(updatedSeat.getSeatNumber(),
				updatedTrainDetails.getTrainNumber());
		if (existingSeat == null) {
			return null;
		}

		existingSeat.setSeatClass(updatedSeat.getSeatClass());
		existingSeat.setPrice(updatedSeat.getPrice());
		existingSeat.setBooked(updatedSeat.isBooked());
		existingSeat.setPassenger(existingSeat.isBooked() ? existingPassenger : null);
		existingSeat.setTrain(existingTrainDetails);

		// update payment details
		List<PaymentDto> updatedPayments = updatedPassenger.getPayment();

		for (PaymentDto updatedPayment : updatedPayments) {
			if (updatedPayment.getTrainNumber().equals(updatedTrainDetails.getTrainNumber())) {
				Payment existingPayment = paymentRepository.findByPaymentIdAndTrainNumber(
						updatedPayment.getPaymentId(), updatedPayment.getTrainNumber());
				existingPayment.setTotalTravelFair(existingSeat.getPrice());
				break;
			}
		}

		// Save changes
		Reservation result = reservationRepository.save(existingReservation);

		return getOneReservation(result.getBookingId(), result.getPassenger().getUserId());
	}

	@Transactional
	@Override
	public String deleteReservation(int bookingId, int userId) {
		ReservationDto existingReservation = getOneReservation(bookingId, userId);
		PassengerDto existingPassenger = existingReservation.getPassenger();
		TrainDetailsDto existingTrainDetail = existingPassenger.getTrainDetails().get(0);

		Passenger passenger = passengerRepository.findById(userId).get();
		int index = 0;
		for (TrainDetails TrainDetail : passenger.getTrainDetails()) {
			if (TrainDetail.getTrainNumber().equals(existingTrainDetail.getTrainNumber())) {
				TrainDetail.setSeatAvailability(existingTrainDetail.getSeatAvailability() + 1);
				for (Seat seat : TrainDetail.getSeats()) {
					if (seat.getPassenger() != null
							&& seat.getPassenger().getUserId() == existingTrainDetail.getSeat().getPassengerId()) {
						seat.setBooked(false);
						seat.setPassenger(null);
						break;
					}
				}
				TrainDetail = null;
				passenger.getTrainDetails().set(index, TrainDetail);
			}
			++index;
		}

		List<Payment> payments = passenger.getPayment();
		index = 0;
		for (Payment payment : payments) {
			if (payment.getPaymentId() == existingPassenger.getPayment().get(0).getPaymentId()) {
				paymentRepository.delete(payment);
				payment = null;
				payments.set(index, payment);
				break;
			}
			++index;
		}

		reservationRepository.deleteByBookingId(bookingId);
		passengerRepository.save(passenger);

		return "Reservation deleted Successfully";
	}

}
