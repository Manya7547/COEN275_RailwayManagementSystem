import axios from "axios";

const BOOKING_API_BASE_URL = "http://localhost:8080/booking";

class BookingService {
  getTrainByArrivalAndDeparture(departure, arrival, departureDate) {
    return axios.get(
      BOOKING_API_BASE_URL +
        "/getTrainsOnArrivalAndDeparture/" +
        departure +
        "/" +
        arrival +
        "/" +
        departureDate
    );
  }

  getAllSeatsFromATrain(TrainNumber) {
    return axios.get(BOOKING_API_BASE_URL + "/getAllSeats/" + TrainNumber);
  }

  bookTrainForAPassenger(userId, TrainDetails) {
    return axios.post(
      BOOKING_API_BASE_URL + "/bookTrains/" + userId,
      TrainDetails
    );
  }

  bookSeatForAPassenger(TrainNumber, userId, seat) {
    return axios.post(
      BOOKING_API_BASE_URL + "/bookSeats/" + TrainNumber + "/" + userId,
      seat
    );
  }
}

export default new BookingService();
