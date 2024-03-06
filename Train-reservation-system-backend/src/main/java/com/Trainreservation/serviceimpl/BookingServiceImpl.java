package com.Trainreservation.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trainreservation.dto.TrainDetailsDto;
import com.Trainreservation.dto.TrainsDto;
import com.Trainreservation.dto.PassengerDto;
import com.Trainreservation.dto.SeatDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Passenger;
import com.Trainreservation.model.Seat;
import com.Trainreservation.repository.BookingRepository;
import com.Trainreservation.repository.PassengerRepository;
import com.Trainreservation.repository.SeatRepository;
import com.Trainreservation.service.BookingService;
import com.Trainreservation.service.PassengerService;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private PassengerService passengerService;

	public List<TrainsDto> mapTrainAndSeatsToTrainDtoAnsSeatDto(List<TrainDetails> TrainDetails) {

		List<TrainsDto> TrainDtos = new ArrayList<TrainsDto>();

		for (TrainDetails TrainDetail : TrainDetails) {
			TrainsDto TrainDto = new TrainsDto();
			TrainDto.setArrival(TrainDetail.getArrival());
			TrainDto.setArrivalDate(TrainDetail.getArrivalDate());
			TrainDto.setArrivalTime(TrainDetail.getArrivalTime());
			TrainDto.setDeparture(TrainDetail.getDeparture());
			TrainDto.setDepartureDate(TrainDetail.getDepartureDate());
			TrainDto.setDepartureTime(TrainDetail.getDepartureTime());
			TrainDto.setTrainNumber(TrainDetail.getTrainNumber());
			TrainDto.setSeatAvailability(TrainDetail.getSeatAvailability());

			List<Seat> seats = TrainDetail.getSeats();
			List<SeatDto> seatDtos = new ArrayList<SeatDto>();
			if (seats != null) {
				for (Seat seat : seats) {
					SeatDto seatDto = new SeatDto();
					seatDto.setBooked(seat.isBooked());
					seatDto.setPrice(seat.getPrice());
					seatDto.setSeatClass(seat.getSeatClass());
					seatDto.setSeatNumber(seat.getSeatNumber());
					if (seat.getPassenger() != null) {
						seatDto.setPassengerId(seat.getPassenger().getUserId());
					} else {
						seatDto.setPassengerId(-1);
					}
					seatDtos.add(seatDto);
				}
			}

			TrainDto.setSeats(seatDtos);
			TrainDtos.add(TrainDto);
		}
		return TrainDtos;

	}
	
	public List<TrainDetailsDto> mapTrainToTrainDto(List<TrainDetails> TrainDetails) {
		
		List<TrainDetailsDto> TrainDtos = new ArrayList<TrainDetailsDto>();

		for (TrainDetails TrainDetail : TrainDetails) {
			TrainDetailsDto TrainDto = new TrainDetailsDto();
			TrainDto.setArrival(TrainDetail.getArrival());
			TrainDto.setArrivalDate(TrainDetail.getArrivalDate());
			TrainDto.setArrivalTime(TrainDetail.getArrivalTime());
			TrainDto.setDeparture(TrainDetail.getDeparture());
			TrainDto.setDepartureDate(TrainDetail.getDepartureDate());
			TrainDto.setDepartureTime(TrainDetail.getDepartureTime());
			TrainDto.setTrainNumber(TrainDetail.getTrainNumber());
			TrainDto.setSeatAvailability(TrainDetail.getSeatAvailability());
			
			List<Seat> seats = TrainDetail.getSeats();
			int minPrice = Integer.MAX_VALUE;
			if (seats != null) {
				for (Seat seat : seats) {
					if(seat.getSeatClass().equalsIgnoreCase("economy") && minPrice > seat.getPrice()) {
						minPrice = seat.getPrice();
					}
				}
				TrainDto.setMinimumSeatPrice(minPrice);
			}
			TrainDto.setSeat(null);
			TrainDtos.add(TrainDto);
		}
		return TrainDtos;
		
	}

	@Override
	public List<TrainsDto> getAllTrainsAndSeatsDetails() {

		List<TrainDetails> TrainDetails = bookingRepository.findAll();
		if (TrainDetails != null)
			return mapTrainAndSeatsToTrainDtoAnsSeatDto(TrainDetails);
		return null;

	}

	@Override
	public List<TrainDetailsDto> getTrainByArrivalAndDeparture(String departure, String arrival, String departureDate) {

		List<TrainDetails> TrainDetails = bookingRepository.findByArrivalAndDepartureAndDepartureDate(arrival,
				departure, departureDate);
		if (TrainDetails != null)
			return mapTrainToTrainDto(TrainDetails);
		return null;

	}

	@Override
	public List<SeatDto> getAllSeatsFromATrain(String TrainNumber) {

		TrainDetails TrainDetail = bookingRepository.findById(TrainNumber).get();

		if (TrainDetail != null) {
			List<Seat> seats = TrainDetail.getSeats();
			List<SeatDto> seatDtos = new ArrayList<SeatDto>();

			if (seats != null) {
				for (Seat seat : seats) {
					SeatDto seatDto = new SeatDto();
					seatDto.setBooked(seat.isBooked());
					seatDto.setPrice(seat.getPrice());
					seatDto.setSeatClass(seat.getSeatClass());
					seatDto.setSeatNumber(seat.getSeatNumber());
					if (seat.getPassenger() != null) {
						seatDto.setPassengerId(seat.getPassenger().getUserId());
					} else {
						seatDto.setPassengerId(-1);
					}
					seatDtos.add(seatDto);
				}
			}
			return seatDtos;
		}
		return null;

	}

	@Override
	public PassengerDto bookTrainForAPassenger(TrainDetails TrainDetails, int userId) {
		if (bookingRepository.findByTrainNumber(TrainDetails.getTrainNumber()).getSeatAvailability() <= 0)
			return null;
		Passenger passenger = passengerRepository.findById(userId).get();
		Passenger passengerTrain = null;
		if (passenger != null) {
			passenger.getTrainDetails().add(TrainDetails);
			passengerTrain = passengerRepository.save(passenger);
		}

		if (passengerTrain != null) {
			PassengerDto passengerDto = passengerService.getPassengerById(passengerTrain.getUserId());
			return passengerDto;
		}
		return null;
	}

	@Override
	public String bookSeatForAPassenger(Seat seat, String TrainNumber, int userId) {
		List<Seat> retrievedSeats = seatRepository.findBySeatNumber(seat.getSeatNumber());
		Passenger passenger = passengerRepository.findById(userId).get();
		Seat seatResult = null;
		if (retrievedSeats != null) {
			for (Seat s : retrievedSeats) {
				if (s.getTrain() != null && s.getTrain().getTrainNumber().equals(TrainNumber) && !s.isBooked()) {
					s.setBooked(true);
					s.setPassenger(passenger);
					seatResult = seatRepository.save(s);

					bookingRepository.updateSeatAvailabilityByTrainNumber(s.getTrain().getSeatAvailability() - 1,
							TrainNumber);
					break;
				}
			}
		}
		if (seatResult != null)
			return "Seats have been chosen and saved successfully";
		return null;
	}

}
