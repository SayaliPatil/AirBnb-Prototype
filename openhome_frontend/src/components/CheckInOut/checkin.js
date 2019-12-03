import React,{ Component } from 'react';
import './checkin.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import {Redirect} from 'react-router';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';
import {Link} from 'react-router-dom';
import axios from 'axios';

class Checkin extends Component {
  constructor(props){
    super(props);
    this.state = {
      bookingDetails : [],
    }
    this.userCheckin = this.userCheckin.bind(this);
    this.userCheckout = this.userCheckout.bind(this);
    this.userBookingCancel = this.userBookingCancel.bind(this);
  
  }
  componentDidMount() {
    var email = UTIL.getUserDetails();
    if(email) {
      axios.get(BASE_URL + '/api/fetchBooking/' + email)
       .then((response) => {
          console.log("response", response)
          if(response.status == 200)
          {
              this.setState({
                bookingDetails : this.state.bookingDetails.concat(response.data)
              })
          }
      });
    }
    else {
      alert("First login as guest to checkin..!!!")
    }
  }
  userCheckin(data) {
    var date = new Date();
    var today = date.toISOString().split('T');
    var time = date.getHours();
    var yesterday = new Date();
    yesterday.setDate(yesterday.getDate() - 1);
    var value = yesterday.toISOString().split('T');
    console.log("TODAYS DATE : " +date[0]);
    console.log("CHECK IN DATE : " +data.check_in_date);
    console.log("CHECK OUT DATE : " +data.check_out_date);
    console.log("GET THE TIME : "+time);
    // console.log("COMPARE DATES :" +date[0] == data.check_in_date);
    console.log("yesterday : " +value);
    if(data.booking_cancelled) {
      alert("User booking has been cancelled");
    }
    else if(data.user_checked_out_flag) {
      alert("User already checked out");
    }
    else if((today[0] == data.check_in_date && time >= 15 && time <= 23) || (value[0] == data.check_in_date && time >= 0 && time <= 4) ) {
        if(data.user_checked_in_flag) {
          alert("User already checked in");
        }
        else {
          data.user_checked_in_flag = true;
          data.no_show = false;
          data.amount_paid = data.price;
          data.user_check_out_date = "";
          console.log("DATA SENT : " +JSON.stringify(data));
          this.updateBooking(data,function alertFunc(){
             alert("User checked in successfully");
          });
        }
      }
    else {
        alert("user can not check in now");
      }
    }

  userCheckout(data) {
    console.log("user_checked_out_flag : " +data.user_checked_out_flag);
    console.log("user_checked_in_flag : " +data.user_checked_in_flag);
      if(data.booking_cancelled) {
        alert("User can not check in.!! Your booking was cancelled");
      }
      else if(data.user_checked_in_flag && !data.user_checked_out_flag) {
        var date = new Date();
        var today = date.toISOString().split('T')[0];
        data.user_checked_out_flag = true;
        data.user_check_out_date = today.toString();
        console.log("TODAYS DATE : " +today);
        this.updateBooking(data,function alertFunc(){
           alert("User checked out successfully");
        });
      }
      else {
        alert("Either user is not checked in or already checkout out");
      }
  }

  userBookingCancel(data) {
    if(data.booking_cancelled) {
      alert("Your booking was already cancelled");
    }
    else if(data.user_checked_out_flag) {
      alert("Your booking can not be cancelled.!! Already checked out.!!");
    }
    else {
      data.booking_cancelled = true;
      this.cancelBooking(data,function alertFunc(){
         alert("Your booking has been cancelled");
      });
    }
  }
  updateBooking(data, callback) {
    fetch(`http://localhost:8080/api/checkinout`, {
           method: 'POST',
           mode: 'cors',
           headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
           body: JSON.stringify(data)
         }).then(response => {
            console.log("Status Code : ",response);
            if(response.status==200) {
             window.location.reload();
            return response.json();
          }
        }).then(result => {
          console.log("Updating booking details Results:",result);
          callback();
        })
    .catch(error => {
      console.log("Error : " + error);
    });

  }

  cancelBooking(data, callback) {
    fetch(`http://localhost:8080/api/cancelbooking`, {
           method: 'POST',
           mode: 'cors',
           headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
           body: JSON.stringify(data)
         }).then(response => {
            console.log("Status Code : ",response);
            if(response.status==200) {
             window.location.reload();
            return response.json();
          }
        }).then(result => {
          console.log("Updating booking details Results:",result);
          callback();
        })
    .catch(error => {
      console.log("Error : " + error);
    });

  }

  render() {
            let bookingInfo = this.state.bookingDetails.map(bookingItem => {
                return (
                    <div className="checkin-class">
                    <div class="row">
                    <button onClick= {() => this.userCheckin(bookingItem)} type="submit" className="btn btn-primary checkin">CHECK-IN</button>
                    <br/>
                    <button onClick= {() => this.userCheckout(bookingItem)} type="submit" className="btn btn-primary checkout">CHECK-OUT</button>
                    <br/><br/>
                    <button onClick= {() => this.userBookingCancel(bookingItem)} type="submit" className="btn btn-primary cancel">CANCEL BOOKING</button>
                    <div class="col-sm-7" className="bookColor_dash_list">
                      <ul class="list-inline">
                        <li>Booking ID : {bookingItem.id}</li>
                        <br></br>
                        <li>Property ID : { bookingItem.property_id}</li>
                        <br></br>
                        <li>Total Bill for Stay: $ {bookingItem.price}</li>
                        <br></br>
                        <li>Reservation Start Date : {bookingItem.check_in_date}</li>
                        <br></br>
                        <li>Reservation End Date : {bookingItem.check_out_date}</li>
                        <br></br>
                      </ul>
                    </div>
                    </div>
                    </div>
                  );
    });
    console.log("Booking details on success page : " +this.state.bookingDetails);
    return (
              <div>
                  <div className="booking-result-class">
                      <h>{this.state.bookingDetails.length}
                          {this.state.bookingDetails.length <= 1 ? " RESULT " : " RESULTS "}  FOUND
                      </h></div>
                      <div>
                          {bookingInfo}
                      </div>
                  <div></div>
              </div>
           );
  }
}
export default Checkin;
