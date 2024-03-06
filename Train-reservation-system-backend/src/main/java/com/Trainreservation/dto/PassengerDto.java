package com.Trainreservation.dto;

import java.util.List;

public class PassengerDto {
	
	private int userId;
    private String emailId;
    private int admin;
    private String firstName;
    private String lastName;
    private List<TrainDetailsDto> TrainDetails;
    private List<PaymentDto> payment;
    
	public PassengerDto() {
	}

	public PassengerDto(int userId, String emailId, int admin, String firstName, String lastName,
			List<TrainDetailsDto> TrainDetails, List<PaymentDto> payment) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.admin = admin;
		this.firstName = firstName;
		this.lastName = lastName;
		this.TrainDetails = TrainDetails;
		this.payment = payment;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<TrainDetailsDto> getTrainDetails() {
		return TrainDetails;
	}

	public void setTrainDetails(List<TrainDetailsDto> TrainDetails) {
		this.TrainDetails = TrainDetails;
	}

	public List<PaymentDto> getPayment() {
		return payment;
	}

	public void setPayment(List<PaymentDto> payment) {
		this.payment = payment;
	}

}
