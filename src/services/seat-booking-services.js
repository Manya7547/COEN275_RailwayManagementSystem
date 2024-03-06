import {LOCAL_BACKEND_BASE_URL} from "../constants";
import axios from 'axios';

export default class SeatBookingServices {
    static getAllSeats = (TrainNumber) => {
        const getAllSeatsURL = LOCAL_BACKEND_BASE_URL + 'booking/getAllSeats/' + TrainNumber;
        return axios.get(getAllSeatsURL)
            .then(response => {
                return response.data;
            })
            .catch(error => {
                console.log("Error in Backend call, sending back empty seats", error);
                return []
            })
    }

    static bookSeats = (TrainNumber, userId, seatNumber, seatClass, price) => {
        const bookSeatsURL = LOCAL_BACKEND_BASE_URL + 'booking/bookSeats/' + TrainNumber + '/' + userId;
        const bookSeatsRequest = {
            seatNumber: seatNumber,
            seatClass: seatClass,
            price: price,
            booked: false
        }

        return axios.post(bookSeatsURL, bookSeatsRequest)
            .then(response => {
                return response.data;
            })
            .catch(error => {
                console.log("Error in Backend call, sending back empty booking", error);
                return null;
            })
    }

    static createReservation = (TrainNumber, userId, seatNumber, price) => {
        const createReservationURL = LOCAL_BACKEND_BASE_URL + 'payment/createReservation/' + TrainNumber + '/' + userId + '/' + seatNumber;
        const createReservation = {
            totalTravelFair: price
        }

        return axios.post(createReservationURL, createReservation)
            .then(response => {
                return response.data;
            })
            .catch(error => {
                console.log("Error in Backend call, sending back empty reservation", error);
                return null;
            })
    }

    static selectTrain = (userId, TrainNumber, departure, arrival, departureDate, departureTime, arrivalDate, arrivalTime) => {
        const selectTrainURL = LOCAL_BACKEND_BASE_URL + 'booking/bookTrains/' + userId;
        const selectTrainRequest = {
            TrainNumber: TrainNumber,
            departure: departure,
            arrival: arrival,
            departureDate: departureDate,
            departureTime: departureTime,
            arrivalDate: arrivalDate,
            arrivalTime: arrivalTime,
            seatAvailability: 30
        }

        return axios.post(selectTrainURL, selectTrainRequest)
            .then(response => {
                return response.data;
            })
            .catch(error => {
                console.log("Error in Backend call, sending back empty select Train", error);
                return null;
            })
    }
}
