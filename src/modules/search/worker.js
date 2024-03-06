import {put, call} from "redux-saga/effects";

import actions from "../../constants/actions";
import {filterBySourceDest} from "../../services/global-services";
import {get} from "../../utils/xhr";

import TrainJSON from "../../mocks/Trains-one-way-mumbai.json";

export function* fetTrainList(payload) {
    // put API URL here
    const url = "http://localhost:8080/booking/getTrainsOnArrivalAndDeparture/" +
        payload.source + "/" + payload.destination + "/" + payload.deptDate;

    const jsonResponse = [...TrainJSON];
    try {
        let response;
        response = yield call(get, url);
        if (!response) {
            response = filterBySourceDest(payload, jsonResponse);
        }

        yield put({
            type: actions.GET_Train_LIST_SUCCESS,
            result: response,
            error: null
        });
    } catch (error) {
        const errorObj = JSON.parse(error.message);

        yield put({
            type: actions.GET_Train_LIST_ERROR,
            result: null,
            error: {
                statusCode: errorObj.statusCode,
                message: errorObj.errorMessage
            }
        });
    }
}
