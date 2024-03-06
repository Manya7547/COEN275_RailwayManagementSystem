package com.Trainreservation.dto;

public class TrainDetailsDto {

	private String TrainNumber;
	private String departure;
	private String arrival;
	private String departureDate;
	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;
	private int seatAvailability;
	private int minimumSeatPrice;
	private SeatDto seat;

	public TrainDetailsDto() {
	}

	public TrainDetailsDto(String TrainNumber, String departure, String arrival, String departureDate,
			String departureTime, String arrivalDate, String arrivalTime, int seatAvailability, int minimumSeatPrice,
			SeatDto seat) {
		super();
		this.TrainNumber = TrainNumber;
		this.departure = departure;
		this.arrival = arrival;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.seatAvailability = seatAvailability;
		this.minimumSeatPrice = minimumSeatPrice;
		this.seat = seat;
	}

	public String getTrainNumber() {
		return TrainNumber;
	}

	public void setTrainNumber(String TrainNumber) {
		this.TrainNumber = TrainNumber;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getSeatAvailability() {
		return seatAvailability;
	}

	public void setSeatAvailability(int seatAvailability) {
		this.seatAvailability = seatAvailability;
	}

	public SeatDto getSeat() {
		return seat;
	}

	public void setSeat(SeatDto seat) {
		this.seat = seat;
	}

	public int getMinimumSeatPrice() {
		return minimumSeatPrice;
	}

	public void setMinimumSeatPrice(int minimumSeatPrice) {
		this.minimumSeatPrice = minimumSeatPrice;
	}

}
