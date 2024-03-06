import axios from "axios";

const BOOKING_API_BASE_URL = "http://localhost:8080/adminControl";

class AdminService {
  saveTrainDetails(admin, Trainobj) {
    return axios.post(BOOKING_API_BASE_URL + "/saveTrain/" + admin, Trainobj);
  }

  getAllTrainsDetails(admin=0) {
    return axios.get(BOOKING_API_BASE_URL + "/getAllTrains/" + admin);
  }

  updateTrainsDetailsAsAdmin(admin, TrainNumber, Trainobj) {
    console.log(Trainobj)
    return axios.put(
      BOOKING_API_BASE_URL + "/updateTrain/" + admin + "/" + TrainNumber,
      Trainobj
    );
  }

  DeleteTrainDetails(admin=0, TrainNumber, Trainobj) {
    return axios.delete(
      BOOKING_API_BASE_URL + "/deleteTrain/" + admin + "/" + TrainNumber,
      {data: Trainobj}
    );
  }

  getAllReservation(TrainNumber) {
    return axios.get(
      BOOKING_API_BASE_URL + "/viewAllReservation/" + TrainNumber
    );
  }
}

export default new AdminService();