import React, { useState } from 'react';
import {
    TextField,
    Button,
    Dialog,
    DialogActions,
    DialogTitle
  } from "@material-ui/core";
import AdminService from "../../services/AdminService";
import "./admin.css";

const AdminEditTrain = (props) => {
    
    const [formData, setFormData] = useState({
        TrainNumber: props.location.state.TrainObj.TrainNumber,
        departure: props.location.state.TrainObj.departure,
        arrival:props.location.state.TrainObj.arrival,
        departureDate:props.location.state.TrainObj.departureDate,
        departureTime: props.location.state.TrainObj.departureTime,
        arrivalDate:props.location.state.TrainObj.arrivalDate,
        arrivalTime:props.location.state.TrainObj.arrivalTime,
        seatAvailability:props.location.state.TrainObj.seatAvailability
      });

    const handleSubmit = (e) =>{
        e.preventDefault()
        // check if any data is empty
        for (const [key, value] of Object.entries(formData)){
          if (value == "") {
            console.log("empty fields!")
            setSelectedValue("Empty fields are not allowed!")
            setNavigateBack(false)
            handleDialogBoxOpen()
            return
          }
        }

        // check that arrival and departure are not same
        if (formData.arrival == formData.departure) {
          setSelectedValue("Arrival and departure cities cannot be same!")
          setNavigateBack(false)
          handleDialogBoxOpen()
          return
        }
        AdminService.updateTrainsDetailsAsAdmin(1, formData.TrainNumber, formData ).then((ret)=>{
            if(ret.data.includes("Updated")){
                setSelectedValue("Train details Updated successfully")
                setNavigateBack(true)
                handleDialogBoxOpen()
                //alert("Train details Updated successfully");
            }else{
              setNavigateBack(false)
              setSelectedValue(ret.data)
              handleDialogBoxOpen()
            }
            console.log(ret.data)
        })
    };

    const [navigateBack, setNavigateBack] = useState(false)
    const [open, setOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState("");
  
    const handleDialogBoxOpen = () => {
      setOpen(true);
    };
  
    const handleDialogBoxClose = (value) => {
      setOpen(false);
      if (navigateBack) {
        props.history.push({pathname:`/admin`})
      }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
          ...prevData,
          [name]: value,
        }));
      };
    return (
        <div style={{ display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        maxWidth: '400px',
        margin: 'auto',
        padding: '3px',
        border: '1px solid #ccc',
        //borderRadius: theme.shape.borderRadius,
        //boxShadow: theme.shadows[3]
        }}>
        <h2>Update Train Information</h2>
   
        <form style ={{width: '70%',
          marginTop: '15px'}} 
          onSubmit={handleSubmit}>
            <TextField
            label="Train Number"
            variant="outlined"
            name="TrainNumber"
            value={formData.TrainNumber}
            disabled
            onChange={handleChange}
            fullWidth
            margin="normal"
            />
             <TextField
                label="Departure"
                variant="outlined"
                name="departure"
                value={formData.departure}
                onChange={handleChange}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Arrival"
                variant="outlined"
                name="arrival"
                value={formData.arrival}
                onChange={handleChange}
                fullWidth
                margin="normal"
            />
          <TextField
                //error="true"
                label="Departure Date"
                variant="outlined"
                name="departureDate"
                placeholder='DD/MM/YYYY'
                type="date"
                value={formData.departureDate}
                onChange={handleChange}
                fullWidth
                margin="normal"
            />
          <TextField
                label="Departure Time"
                variant="outlined"
                name="departureTime"
                type="time"
                placeholder='HH:MM'
                value={formData.departureTime}
                onChange={handleChange}
                fullWidth
                margin="normal"
                InputLabelProps={{
                    shrink: true,
                  }}
            />
              <TextField
                label="Arrival Date"
                variant="outlined"
                name="arrivalDate"
                placeholder='DD/MM/YYYY'
                type="date"
                value={formData.arrivalDate}
                onChange={handleChange}
                fullWidth
                margin="normal"
            />
            <TextField
                label="Arrival Time"
                variant="outlined"
                name="arrivalTime"
                type="time"
                placeholder='HH:MM'
                value={formData.arrivalTime}
                onChange={handleChange}
                fullWidth
                margin="normal"
                InputLabelProps={{
                    shrink: true,
                  }}
            />
             <TextField
                label="Seat Availability"
                variant="outlined"
                name="seatAvailability"
                value={formData.seatAvailability}
                onChange={handleChange}
                fullWidth
                margin="normal"
            />
          {/* Add other form fields as needed */}
  
          <Button className="black-button"
            type="submit"
            variant="contained"
            color="primary"
            size="large"
            style={{ marginTop: '16px' }}
          >
            Update
          </Button>
        </form>
        <Dialog
        open={open}
        onClose={handleDialogBoxClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">
            {selectedValue}
          </DialogTitle>
          <DialogActions alignItems="center">
            <Button onClick={handleDialogBoxClose} autoFocus>
              Close
            </Button>
          </DialogActions>
        </Dialog>
      </div>
    )
}

export default AdminEditTrain;