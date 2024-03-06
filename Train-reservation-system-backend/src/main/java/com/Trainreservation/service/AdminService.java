package com.Trainreservation.service;

import java.util.List;
import com.Trainreservation.dto.TrainsDto;
import com.Trainreservation.dto.ReservationDto;
import com.Trainreservation.model.TrainDetails;

public interface AdminService {

	String saveTrainDetailsToDB(TrainDetails Trainobj, int admin);

	List<TrainsDto> getAllTrainsDetails(int admin);

	String updateTrainsDetailsAsAdminToDB(TrainDetails Trainobj, int admin, String TrainNumber);

	String deleteTrainFromDB(TrainDetails Trainobj, int admin, String TrainNumber);

	List<ReservationDto> getAllReservation(String TrainNumber);

}
