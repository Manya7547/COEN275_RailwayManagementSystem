package com.Trainreservation.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Trainreservation.dto.TrainsDto;
import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.dto.SeatDto;
import com.Trainreservation.model.TrainDetails;
import com.Trainreservation.model.Passenger;
import com.Trainreservation.model.Seat;
import com.Trainreservation.repository.AdminRepository;
import com.Trainreservation.repository.BookingRepository;
import com.Trainreservation.repository.ReservationRepository;
import com.Trainreservation.service.AdminService;
import com.Trainreservation.service.ManageReservationService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ManageReservationService manageReservationService;

	@Override
	public String saveTrainDetailsToDB(TrainDetails Trainobj, int admin) {
		List<Passenger> passenger = adminRepository.findByAdmin(admin);
		for (Passenger passengerobj : passenger) {
			if (passengerobj.getAdmin() == 1) {
				bookingRepository.save(Trainobj);
				return "Admin checked! new Train details is been added successfully";
			}
		}
		return "Admin not checked! Cannot add Trains!";
	}

	@Override
	public List<TrainsDto> getAllTrainsDetails(int admin) {
		List<Passenger> passenger = adminRepository.findByAdmin(admin);
		System.out.println(passenger);
		List<TrainsDto> TrainDtos = new ArrayList<TrainsDto>();
		List<TrainDetails> TrainDetail = bookingRepository.findAll();
		for (Passenger passengerobj : passenger) {
			// if (passengerobj.getAdmin() == 1) {
				for (TrainDetails TrainDetailnew : TrainDetail) {
					TrainsDto TrainDto = new TrainsDto();
					TrainDto.setArrival(TrainDetailnew.getArrival());
					TrainDto.setArrivalDate(TrainDetailnew.getArrivalDate());
					TrainDto.setArrivalTime(TrainDetailnew.getArrivalTime());
					TrainDto.setDeparture(TrainDetailnew.getDeparture());
					TrainDto.setDepartureDate(TrainDetailnew.getDepartureDate());
					TrainDto.setDepartureTime(TrainDetailnew.getDepartureTime());
					TrainDto.setTrainNumber(TrainDetailnew.getTrainNumber());
					TrainDto.setSeatAvailability(TrainDetailnew.getSeatAvailability());
					List<Seat> seats = TrainDetailnew.getSeats();
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
			// }
		}
		return TrainDtos;
	}

	@Override
	public String updateTrainsDetailsAsAdminToDB(TrainDetails Trainobj, int admin, String TrainNumber) {
		List<Passenger> passenger = adminRepository.findByAdmin(admin);
		TrainDetails Trainold = bookingRepository.findById(TrainNumber).get();
		for (Passenger passengerobj : passenger) {
			if (passengerobj.getAdmin() == 1) {
				if (Trainold.getSeatAvailability() == 30) {
					Trainold.setTrainNumber(Trainobj.getTrainNumber());
					Trainold.setDeparture(Trainobj.getDeparture());
					Trainold.setArrival(Trainobj.getArrival());
					Trainold.setDepartureDate(Trainobj.getDepartureDate());
					Trainold.setDepartureTime(Trainobj.getDepartureTime());
					Trainold.setArrivalDate(Trainobj.getArrivalDate());
					Trainold.setArrivalTime(Trainobj.getArrivalTime());
					Trainold.setSeatAvailability(Trainobj.getSeatAvailability());
					bookingRepository.save(Trainold);
					return "Adminchecked! Train Deatails Updated";
				}
				return "Train details cannot update as it already allowed to be booked";
			}
		}
		return "Cannot Update Train!";
	}

	@Override
	public String deleteTrainFromDB(TrainDetails Trainobj, int admin, String TrainNumber) {
		List<Passenger> passenger = adminRepository.findByAdmin(admin);
		// List<TrainDetails> TrainsDeat = bookingRepository.findAll();
		TrainDetails Trainold = bookingRepository.findById(TrainNumber).get();
		for (Passenger passengerobj : passenger) {
			if (passengerobj.getAdmin() == 1) {
				if (Trainobj.getSeatAvailability() == 30) {
					bookingRepository.delete(Trainold);
					return "Admin checked! Train Deatails Deleted Successfully";
				}
				return "Train details cannot be deleted as this Train already exists bookings";
			}
		}
		return "Sorry! Cannot Delete Train Details";
	}

	@Override
	public List<ReservationDto> getAllReservation(String TrainNumber) {
		return manageReservationService.getAllReservationBasedOnTrainNumber(TrainNumber);
	}

}
