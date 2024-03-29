package com.Trainreservation.model;

import java.io.Serializable;
import java.util.Objects;

public class CompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String seatNumber;
	private String Train;

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getTrain() {
		return Train;
	}

	public void setTrain(String Train) {
		this.Train = Train;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Train, seatNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKey other = (CompositeKey) obj;
		return Objects.equals(Train, other.Train) && Objects.equals(seatNumber, other.seatNumber);
	}

}
