import { takeLatest, call } from "redux-saga/effects";

import actions from "../../constants/actions";
import * as worker from "./worker";

export function* watchTrainSearch() {
  yield takeLatest(actions.GET_Train_LIST, function* (action) {
    yield call(worker.fetTrainList, action.payload);
  });
}
