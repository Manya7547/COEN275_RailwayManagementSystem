package com.Trainreservation.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class TrainDetails {

	@Id
	private String TrainNumber;
	private String departure;
	private String arrival;
	private String departureDate;
	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;

	private int seatAvailability;

	@OneToMany(mappedBy = "Train", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Seat> seats;

	@ManyToMany(mappedBy = "TrainDetails", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @JsonIgnore
	private List<Passenger> passengers;

	public TrainDetails() {

	}

	public TrainDetails(String TrainNumber, String departure, String arrival, String departureDate,
			String departureTime, String arrivalDate, String arrivalTime, int seatAvailability, List<Seat> seats,
			List<Passenger> passengers) {
		super();
		this.TrainNumber = TrainNumber;
		this.departure = departure;
		this.arrival = arrival;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.seatAvailability = seatAvailability;
		this.seats = seats;
		this.passengers = passengers;
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

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public int getSeatAvailability() {
		return seatAvailability;
	}

	public void setSeatAvailability(int seatAvailability) {
		this.seatAvailability = seatAvailability;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrival, arrivalDate, arrivalTime, departure, departureDate, departureTime, TrainNumber,
				passengers, seatAvailability, seats);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainDetails other = (TrainDetails) obj;
		return Objects.equals(arrival, other.arrival) && Objects.equals(arrivalDate, other.arrivalDate)
				&& Objects.equals(arrivalTime, other.arrivalTime) && Objects.equals(departure, other.departure)
				&& Objects.equals(departureDate, other.departureDate)
				&& Objects.equals(departureTime, other.departureTime)
				&& Objects.equals(TrainNumber, other.TrainNumber) && Objects.equals(passengers, other.passengers)
				&& seatAvailability == other.seatAvailability && Objects.equals(seats, other.seats);
	}


}
