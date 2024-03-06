package com.Trainreservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Trainreservation.model.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	Payment findByPaymentIdAndTrainNumber(int paymentId, String TrainNumber);

}
