package com.Trainreservation.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId;
	private int totalTravelFair;
	private String TrainNumber;

	public Payment() {
	}

	public Payment(int paymentId, int totalTravelFair, String TrainNumber) {
		super();
		this.paymentId = paymentId;
		this.totalTravelFair = totalTravelFair;
		this.TrainNumber = TrainNumber;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getTotalTravelFair() {
		return totalTravelFair;
	}

	public void setTotalTravelFair(int totalTravelFair) {
		this.totalTravelFair = totalTravelFair;
	}

	public String getTrainNumber() {
		return TrainNumber;
	}

	public void setTrainNumber(String TrainNumber) {
		this.TrainNumber = TrainNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(TrainNumber, paymentId, totalTravelFair);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(TrainNumber, other.TrainNumber) && paymentId == other.paymentId
				&& totalTravelFair == other.totalTravelFair;
	}

}
