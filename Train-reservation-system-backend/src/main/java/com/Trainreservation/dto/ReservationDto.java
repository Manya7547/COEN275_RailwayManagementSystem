package com.Trainreservation.dto;

public class ReservationDto {

	private int bookingId;
	private String Departure;
	private String arrival;
	private PassengerDto passenger;
	private String TrainNumber;
	
	public ReservationDto() {
	
	}
	
	public ReservationDto(int bookingId, String departure, String arrival, PassengerDto passenger,
			String TrainNumber) {
		super();
		this.bookingId = bookingId;
		Departure = departure;
		this.arrival = arrival;
		this.passenger = passenger;
		this.TrainNumber = TrainNumber;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getDeparture() {
		return Departure;
	}

	public void setDeparture(String departure) {
		Departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public PassengerDto getPassenger() {
		return passenger;
	}

	public void setPassenger(PassengerDto passenger) {
		this.passenger = passenger;
	}

	public String getTrainNumber() {
		return TrainNumber;
	}

	public void setTrainNumber(String TrainNumber) {
		this.TrainNumber = TrainNumber;
	}

}
