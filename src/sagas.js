import { fork } from "redux-saga/effects";

import * as TrainSearch from "./modules/search/watcher";

export default function* rootSaga() {
  yield [fork(TrainSearch.watchTrainSearch)];
}
