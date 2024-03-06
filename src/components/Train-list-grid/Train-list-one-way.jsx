import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import {
  Avatar,
  Button,
  Card,
  CardContent,
  CircularProgress,
  Grid,
  Typography,
  Table,
  TableBody,
  TableCell,
  TablePagination,
  TableRow
} from "@material-ui/core";

import { useGoogleLogin } from "@react-oauth/google";
import GoogleServiceSingleton from "../../services/google-service-singleton";
import { useUserInfoSession } from "../header/user-context";
import SeatBookingServices from "../../services/seat-booking-services";

const useStyles = makeStyles(() => ({
  textAlign: {
    textAlign: "right"
  },
  textAlignCenter: {
    textAlign: "center"
  },
  cardContainer: {
    marginBottom: 5
  }
}));

const TrainListOneWay = (props) => {
  const { TrainList, bookNow } = props;
  const classes = useStyles();
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [loginDone, setLoginDone] = useState(false);
  const [TrainSelected, setTrainSelected] = useState("");
  const [departureCity, setDepartureCity] = useState("");
  const [arrivalCity, setArrivalCity] = useState("");
  const [departureDate, setDepartureDate] = useState("");
  const [departureTime, setDepartureTime] = useState("");
  const [arrivalDate, setArrivalDate] = useState("");
  const [arrivalTime, setArrivalTime] = useState("");

  const [randomNumber, setRandomNumber] = useState(0); // Add this state for storing the random number

  const history = useHistory();
  let component = null;

  // Get session details from cache
  const { userInfoSession, updateUserInfoSession, appendToUserInfoSession } = useUserInfoSession();

  useEffect(() => {
    if (userInfoSession) {
      setLoginDone(true);
    }
  }, [userInfoSession]);

  const login = useGoogleLogin({
    onSuccess: async (tokenResponse) => {
      try {
        const userInfo = await GoogleServiceSingleton.getUserInfo(tokenResponse.access_token);
        setLoginDone(true);
        updateUserInfoSession({
          ...userInfo,
          selectedTrainNumber: TrainSelected,
          departureCity: departureCity,
          arrivalCity: arrivalCity,
          departureDate: departureDate,
          departureTime: departureTime,
          arrivalDate: arrivalDate,
          arrivalTime: arrivalTime,
        });
        const selectTrainData = await SeatBookingServices.selectTrain(
          userInfo.userId,
          TrainSelected,
          departureCity,
          arrivalCity,
          departureDate,
          departureTime,
          arrivalDate,
          arrivalTime,
        )
        console.log("Select Train response: ", selectTrainData);

        // Redirect to /seat-selection
        history.push("/seat-selection");
      } catch (error) {
        // Handle error if user info cannot be retrieved
        console.error("Error fetching user info:", error);
      }
    },
  });

  const handleTrainSelection = async (TrainDetail) => {
    setTrainSelected(TrainDetail.TrainNumber);
    setDepartureCity(TrainDetail.departure);
    setArrivalCity(TrainDetail.arrival);
    setDepartureDate(TrainDetail.departureDate);
    setDepartureTime(TrainDetail.departureTime);
    setArrivalTime(TrainDetail.arrivalTime);
    setArrivalDate(TrainDetail.arrivalDate);
    if (!userInfoSession) {
      login();
    } else {
      const selectTrainData = await SeatBookingServices.selectTrain(
        userInfoSession.userId,
        TrainDetail.TrainNumber,
        TrainDetail.departure,
        TrainDetail.arrival,
        TrainDetail.departureDate,
        TrainDetail.departureTime,
        TrainDetail.arrivalDate,
        TrainDetail.arrivalTime,
      )
      console.log("Select Train response: ", selectTrainData);
      appendToUserInfoSession({
        selectedTrainNumber: TrainDetail.TrainNumber,
        departureCity: TrainDetail.departure,
        arrivalCity: TrainDetail.arrival,
        departureDate: TrainDetail.departureDate,
        departureTime: TrainDetail.departureTime,
        arrivalDate: TrainDetail.arrivalDate,
        arrivalTime: TrainDetail.arrivalTime,
      })
      history.push("/seat-selection");
    }
  };

  if (TrainList?.loading) {
    component = <CircularProgress />;
  } else if (TrainList?.result?.length > 0) {
    component = (
      <Table>
        <TableBody>
          {TrainList.result
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((val, index) => {
              const randomPrice = val?.minimumSeatPrice + Math.floor(randomNumber * 399) - 199;
              return (
                <TableRow key={index}>
                  <TableCell>
                    <Card className={classes.cardContainer}>
                      <CardContent>
                        <Grid container>
                          <Grid item xs={2}>
                            <Avatar
                              src={val.airlineLogo}
                              alt={val.airlineName}
                            />
                          </Grid>
                          <Grid
                            item
                            xs={2}
                            className={classes.textAlignCenter}
                          >
                            <Typography align="center">
                              {val.departureTime}
                            </Typography>
                            <Typography variant="caption">
                              {val.departure}
                            </Typography>
                          </Grid>
                          <Grid
                            item
                            xs={2}
                            className={classes.textAlignCenter}
                          >
                            <Typography>{val.airlineName}</Typography>
                            <Typography variant="caption">
                              {val.TrainNumber}
                            </Typography>
                            <br />
                            <Typography variant="caption">
                              {val.noOfStops === "0"
                                ? `No Stops`
                                : `Non-Stop`}
                            </Typography>
                          </Grid>
                          <Grid
                            item
                            xs={2}
                            className={classes.textAlignCenter}
                          >
                            <Typography>{val.arrivalTime}</Typography>
                            <Typography variant="caption">
                              {val.arrival}
                            </Typography>
                          </Grid>
                          <Grid item xs={4} className={classes.textAlign}>
                            <Button
                              variant="contained"
                              style={{ backgroundColor: "#01377D", color: "white" }}
                              onClick={() => handleTrainSelection(val)}
                            >
                              {`$ ${randomPrice}`}
                            </Button>
                          </Grid>
                        </Grid>
                      </CardContent>
                    </Card>
                  </TableCell>
                </TableRow>
              );
            })}
        </TableBody>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={TrainList.result.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={(event, newPage) => setPage(newPage)}
          onChangeRowsPerPage={(event) => {
            setRowsPerPage(parseInt(event.target.value, 10));
            setPage(0);
          }}
        />
      </Table>
    );
  } else if (TrainList?.result?.length === 0) {
    component = <Typography>{`No Records Found..`}</Typography>;
  } else if (TrainList?.error) {
    component = <Typography>{`Unable to fetch Data...`}</Typography>;
  }

  useEffect(() => {
    setRandomNumber(Math.random()); // Generate the random number once
  }, []);

  return (
    <Grid container>
      <Grid item xs={12}>
        {component}
      </Grid>
    </Grid>
  );
};

TrainListOneWay.propTypes = {
  classes: PropTypes.object,
  TrainList: PropTypes.object,
  bookNow: PropTypes.func,
};

export default TrainListOneWay;
