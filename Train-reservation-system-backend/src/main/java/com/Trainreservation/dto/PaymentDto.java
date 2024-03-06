package com.Trainreservation.dto;

public class PaymentDto {

	private int paymentId;
	private int totalTravelFair;
	private String TrainNumber;

	public PaymentDto() {

	}

	public PaymentDto(int paymentId, int totalTravelFair, String TrainNumber) {
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

}
