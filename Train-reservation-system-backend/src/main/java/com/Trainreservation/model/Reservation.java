package com.Trainreservation.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
	private String Departure;
	private String arrival;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Passenger passenger;
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private TrainDetails Train;

	public Reservation() {
	}

	public Reservation(int bookingId, String departure, String arrival, Passenger passenger, TrainDetails Train) {
		super();
		this.bookingId = bookingId;
		Departure = departure;
		this.arrival = arrival;
		this.passenger = passenger;
		this.Train = Train;
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

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public TrainDetails getTrain() {
		return Train;
	}

	public void setTrain(TrainDetails Train) {
		this.Train = Train;
	}

}
