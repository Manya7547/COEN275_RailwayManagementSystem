package com.Trainreservation.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trainreservation.dto.TrainDetailsDto;
import com.Trainreservation.dto.PassengerDto;
import com.Trainreservation.dto.PaymentDto;
import com.Trainreservation.dto.SeatDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Passenger;
import com.Trainreservation.model.Payment;
import com.Trainreservation.model.Seat;
import com.Trainreservation.repository.PassengerRepository;
import com.Trainreservation.service.PassengerService;

@Service
public class PassengerServiceImpl implements PassengerService {

	@Autowired
	private PassengerRepository passengerRepository;

	@Override
	public PassengerDto savePassengerDetailsToDB(Passenger passengerObj) {
		Passenger passenger = passengerRepository.findByEmailIdAndLastName(passengerObj.getEmailId(),
				passengerObj.getLastName());
		PassengerDto passengerDto = new PassengerDto();
		if (passenger != null) {
			    passengerDto.setUserId( passenger.getUserId()); 
			    passengerDto.setAdmin(passenger.getAdmin());
			    passengerDto.setEmailId(passenger.getEmailId());
			    passengerDto.setFirstName(passenger.getFirstName());
			    passengerDto.setLastName(passenger.getLastName());
		} else {
			Passenger savedPassenger = passengerRepository.save(passengerObj);
			passengerDto.setUserId( savedPassenger.getUserId()); 
		    passengerDto.setAdmin(savedPassenger.getAdmin());
		    passengerDto.setEmailId(savedPassenger.getEmailId());
		    passengerDto.setFirstName(savedPassenger.getFirstName());
		    passengerDto.setLastName(savedPassenger.getLastName());
		}
		return passengerDto;
	}

	@Override
	public PassengerDto updatePassengerDetails(Passenger passenger, int userId) {
		Passenger retrievedPassenger = passengerRepository.findById(userId).get();
		PassengerDto passengerDto = null;
		if (retrievedPassenger != null) {
			retrievedPassenger.setAdmin(passenger.getAdmin());
			retrievedPassenger.setEmailId(passenger.getEmailId());
			retrievedPassenger.setFirstName(passenger.getFirstName());
			retrievedPassenger.setLastName(passenger.getLastName());
			retrievedPassenger.setUserId(userId);
			Passenger passengerResult = passengerRepository.save(retrievedPassenger);
			passengerDto = getPassengerById(passengerResult.getUserId());
		}
		return passengerDto;
	}

	@Override
	public PassengerDto getPassengerById(int userId) {
		Passenger passenger = passengerRepository.findById(userId).get();

		if (passenger != null) {
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

			if (TrainDetails != null) {
				for (TrainDetails TrainDetail : TrainDetails) {
					TrainDetailsDto TrainDetailsDto = new TrainDetailsDto();
					TrainDetailsDto.setArrival(TrainDetail.getArrival());
					TrainDetailsDto.setArrivalDate(TrainDetail.getArrivalDate());
					TrainDetailsDto.setArrivalTime(TrainDetail.getArrivalTime());
					TrainDetailsDto.setDeparture(TrainDetail.getDeparture());
					TrainDetailsDto.setDepartureDate(TrainDetail.getDepartureDate());
					TrainDetailsDto.setDepartureTime(TrainDetail.getDepartureTime());
					TrainDetailsDto.setTrainNumber(TrainDetail.getTrainNumber());
					TrainDetailsDto.setSeatAvailability(TrainDetail.getSeatAvailability());

					List<Seat> seats = TrainDetail.getSeats();
					SeatDto seatDto = new SeatDto();
					if (seats != null) {
						for (Seat seat : seats) {
							if (seat.getPassenger() != null
									&& seat.getPassenger().getUserId() == passenger.getUserId()) {
								seatDto.setBooked(seat.isBooked());
								seatDto.setPrice(seat.getPrice());
								seatDto.setSeatClass(seat.getSeatClass());
								seatDto.setSeatNumber(seat.getSeatNumber());
							}
						}
					}

					TrainDetailsDto.setSeat(seatDto);
					TrainDetailsDtos.add(TrainDetailsDto);
				}
			}
			if (payments != null) {
				for (Payment payment : payments) {
					PaymentDto paymentDto = new PaymentDto();
					paymentDto.setPaymentId(payment.getPaymentId());
					paymentDto.setTotalTravelFair(payment.getTotalTravelFair());
					paymentDto.setTrainNumber(payment.getTrainNumber());

					paymentDtos.add(paymentDto);
				}
			}

			passengerDto.setTrainDetails(TrainDetailsDtos);
			passengerDto.setPayment(paymentDtos);

			return passengerDto;
		}
		return null;
	}

}
