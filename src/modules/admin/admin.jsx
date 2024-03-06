import React, {useState} from "react";
import {
  Grid,
  TextField,
  Button,
  ButtonGroup,
} from "@material-ui/core";
import AdminService from "../../services/AdminService";
import DataTable from 'react-data-table-component';
import { Edit, Delete } from "@material-ui/icons";
import "./admin.css";

const AdminControl = (props) => {
  const Traincolumns = [
    {
      id:"TrainNumber",
      name: "Train ID",
      selector: row => row.TrainNumber
    },
    {
      id:"departure",
      name: "Departure",
      selector: row => row.departure
    },
    {
      id:"arrival",
      name: "Arrival",
      selector: row => row.arrival
    },
    {
      id:"departureDate",
      name: "DepartureDate",
      selector: row => row.departureDate
    },
    {
      id:"departureTime",
      name: "DepartureTime",
      selector: row => row.departureTime
    },
    {
      id:"arrivalDate",
      name: "ArrivalDate",
      selector: row => row.arrivalDate
    },
    {
      id:"arrivalTime",
      name: "ArrivalTime",
      selector: row => row.arrivalTime
    },
    {
      id:"seatAvailability",
      name: "SeatAvailability",
      selector: row => row.seatAvailability
    },{
      id:"ActionID",
      name:"Action",
      cell: (row, index, column, id) => (
        <ButtonGroup variant="contained" aria-label="outlined primary button group">
            <Button
              startIcon = {<Edit></Edit>}
              onClick={() => handleEditClick(row,"edit")}
            >
            </Button>
            <Button
              startIcon = {<Delete></Delete>}
              onClick={(e) => handleDeleteClick(e, index, row, "delete")}
            >
            </Button>
          </ButtonGroup>
          
      ),
    
    ignoreRowClick: true,
    allowOverflow: true,
    button: true,
  }
  ];
  const reservationcolumns = [
    {
      id:"bookingId",
      name: "Booking-Id",
      selector: row => row.bookingId
    },
    {
      id:"departure",
      name: "Departure",
      selector: row => row.departure
    },
    {
      id:"arrival",
      name: "Arrival",
      selector: row => row.arrival
    },
    {
      id:"departureDate",
      name: "DepartureDate",
      selector: row => row.passenger.TrainDetails[0].departureDate
    },
    {
      id:"departureTime",
      name: "DepartureTime",
      selector: row => row.passenger.TrainDetails[0].departureTime
    },
    {
      id:"arrivalDate",
      name: "Arrival Date",
      selector: row => row.passenger.TrainDetails[0].arrivalDate
    },
    {
      id:"arrivalTime",
      name: "Arrival Time",
      selector: row => row.passenger.TrainDetails[0].arrivalTime
    },
    {
      id:"emailId",
      name: "Email-Id",
      selector: row => row.passenger.emailId
    },
    {
      id:"firstName",
      name: "FirstName",
      selector: row => row.passenger.firstName
    },
    {
      id:"lastName",
      name: "LastName",
      selector: row => row.passenger.lastName
    },
    {
      id:"TrainNumber",
      name: "Train Number",
      selector: row => row.TrainNumber
    },
    {
      id:"seatNumber",
      name: "Seat-Number",
      selector: row => row.passenger.TrainDetails[0].seat.seatNumber
    },
    {
      id:"seatClass",
      name: "Seat-Class",
      selector: row => row.passenger.TrainDetails[0].seat.seatClass
    },
    {
      id:"totalTravelFair",
      name: "TotalTravelFair",
      selector: row => row.passenger.payment[0].totalTravelFair
    }
  ];
 // const [pending, setPending] = React.useState(true);
  const [rows, setRows] = useState("")
  //const [reservationRows, setReservationRows] = useState("")
  //const [columns1, setColumns] = React.useState(Traincolumns)
  const [TrainNumber, setTrainNumber] = useState("")
  const [dataTable, setTable] = useState("")

    const fetchTrainData = () => {
        //console.log(admin);
        AdminService.getAllTrainsDetails(
        ).then((ret) => {
          console.log("result", ret.data);
          var returndata = ret.data
          delete returndata["seats"]
          var keys = Object.keys(returndata[0])
          setTable("")

          setTable(
            <>
            <DataTable
            title="Train List"
            columns={Traincolumns}
            data={returndata} 
            //progressPending={pending} 
            pagination/>

            <div style={{ marginRight: "10px" }}>
            <Button className="black-button"
                    type="submit"
                    variant="contained"
                    color="primary"
                    onClick={() => handleAddTrain()}
                    style={{ marginTop: "15px", marginLeft: "500px"}}
                  >
                   Add Trains
              </Button>
          </div>
          </>
            )
        });
      };
      const fetchReservation = () =>{
        console.log(TrainNumber);
        if (TrainNumber == "") {
          alert("Train number is empty");
          return
        }
        AdminService.getAllReservation(TrainNumber).then((ret) =>{
          console.log("result", ret.data);
          var returndata = ret.data
          setTable("")
          setTable(<DataTable
            title = {"ReservationList for Train number: ".concat(TrainNumber)}
            columns={reservationcolumns}
            data={returndata} 
            //progressPending={pending} 
            pagination/>)
        })
      }
      const handleTrainNumber = (e) => {
        setTrainNumber(e.target.value);
      };
      const handleSubmit = () =>{
        fetchTrainData();
      }
      const handleReservationSubmit = (e) =>{
        e.preventDefault();
        fetchReservation();
      }

      const handleAddTrain = () =>{
        console.log("handle add");
        props.history.push({pathname:`/add-Train`})
      }

      const handleEditClick = (TrainObj) => {
        console.log("handle edit");
        props.history.push({pathname:`/edit-Train/${TrainObj.TrainNumber}`, state:{TrainObj}});
      };

      const handleDeleteClick = (e, rowId, TrainObj, actionType) => {
        e.preventDefault()
        if (actionType == "delete") {
          console.log("Marked for delete: %s", TrainObj.TrainNumber)
          AdminService.DeleteTrainDetails(1, TrainObj.TrainNumber, TrainObj).then((ret) =>{
            console.log(ret.data)
            if (ret.data.includes("Deleted Successfully")) {
              alert("Train details deleted successfully")
              document.getElementById("row-"+rowId).remove()
            } else {
              alert(ret.data)
            }
          })
        } else {
          console.log("Marked for edit: %s", TrainNumber) 
        }
      }
     
    return (
      <><Grid container spacing={2}>
        <Grid item xs={12}>
          <h2>Hello There!!</h2>
        </Grid>
        <div style={{ display: "flex", justifyContent: "flex-end" , alignItems: "center"}}>
        <div style={{ marginRight: "20px" }}>
          <Button className="black-button"
                  type="submit"
                  variant="contained"
                  color="primary"
                  onClick={() => handleSubmit()}
                  style={{ marginTop: "15px", marginLeft: "10px" }}
                >
                  All Trains
            </Button>
        </div>
          <form onSubmit={handleReservationSubmit} style={{ marginLeft: "10px" }}>
          <div style={{ display: "flex", alignItems: "center" , paddingLeft:"250px"}}>
            <TextField
              label="Train Number"
              type="text"
              value={TrainNumber}
              onChange={handleTrainNumber}
              variant="outlined"
              style={{ marginBottom: "10px" ,marginTop: "15px", marginLeft: "10px",paddingTop:"10px"}}
            />
              <Button className="black-button"
                type="submit"
                variant="contained"
                color="primary"
                style={{ marginTop: "15px", marginLeft: "10px", backgroundColor: '#ccc', color: '#000', marginLeft: '10px' }}
              >All Reservations
              </Button>
              </div>
          </form>
        </div>
        
      </Grid>
      {dataTable}
          </>
    )
};
export default AdminControl;