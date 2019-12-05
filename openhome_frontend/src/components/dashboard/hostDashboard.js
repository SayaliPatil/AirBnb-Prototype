import React,{ Component } from 'react';
import './../Properties/Properties.css';
import './dashboard.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import {Redirect} from 'react-router';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Header from './../header/header.js';
import UserBillingSummary from './userBillingSummary.js';

class HostDashboard extends Component {
  constructor(props){
    super(props);
    this.state = {
      bookingDetails : [],
      propertyList : [],
      month:' ',
      propertySelected : '',
      propertySelectedFlag : false,
      status : '',
    }
    this.cancelProperty = this.cancelProperty.bind(this);
  }
  fetchBillingDetails() {
    this.state.propertySelectedFlag = true;
        const data = {
          email : UTIL.getUserDetails(),
          month : this.state.month,
          id : this.state.propertySelected,
        }
        fetch(`${BASE_URL}/api/user/fetchHostDashBoard/`, {
           method: 'POST',
           mode: 'cors',
           headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
           body: JSON.stringify(data)
         }).then(response => {
            console.log("Status Code : ",response);
            if(response.status==200) {
              return response.json();
          }
        }).then(result => {
          console.log("Host Dashboard Results:",result);
          this.setState({
            bookingDetails : result
          })
        })

  }
  componentDidMount() {
    var email = UTIL.getUserDetails();
    if(email) {
      axios.get(BASE_URL + '/api/fetchHostDashBoard/' + email)
       .then((response) => {
          console.log("response", response)
          if(response.status == 200)
          {
              this.setState({
                propertyList : this.state.propertyList.concat(response.data)
              })
          }
      });
    }
    else {
      alert("First login as guest to checkin..!!!")
    }
  }
cancelProperty(data){
  if(this.state.status == 'CHECKED-OUT') {
    alert("Booking is in past and cannot be cancelled");
  }
  else if(this.state.status == 'CANCELLED') {
    alert("Booking is already cancelled");
  }
  else if(this.state.status == 'CHECKED-IN' || this.state.status == 'FUTURE BOOKING') {
      //cancelProperty
      axios.get(BASE_URL + '/cancelProperty/' + data.id)
       .then((response) => {
          console.log("response", response)
          if(response.status == 200)
          {
              alert("Booking cancelled successfully");
          }
      });
  }
}

  render() {
    var sum = 0;
    let properties = this.state.propertyList;
        let options = properties.length > 0
    		&& properties.map((item, i) => {
    		return (
    			<option key={i} value={item.id}>{item.id}</option>
    		)
    	}, this);
    return(
    <div >
          <Header/>
          <br/>
           <div className="guest-billing-info">
                  <h2 className="dashboard-guest-heading"> Host Billing Summary </h2>
                  <table className="table table-hover">
                      <thead className="active">
                        <tr>
                        <th >Booking ID</th>
                        <th >Property ID</th>
                        <th>Booking Start Date</th>
                        <th>Booking End Date</th>
                        <th >Booking Amount</th>
                        <th> Status </th>
                        <th> Management </th>
                        </tr>
                      </thead>
                      {
                          this.state.bookingDetails.length != 0 ?
                            this.state.bookingDetails.map((booking) => {

                              var bookingAmount = 0;
                            if(booking.user_checked_in_flag && booking.user_checked_out_flag && !booking.booking_cancelled) {
                              this.state.status = "CHECKED-OUT";
                              bookingAmount = booking.price;
                            }
                            else if(booking.user_checked_in_flag && !booking.user_checked_out_flag && !booking.booking_cancelled) {
                              this.state.status  = "CHECKED-IN";
                              bookingAmount = booking.price;
                              this.state.buttonEnable = true;
                            }
                            else if(booking.booking_cancelled) {
                              this.state.status  = "CANCELLED";
                              bookingAmount = booking.amount_paid;
                            }
                            else if(!booking.user_checked_in_flag && !booking.user_checked_out_flag && !booking.booking_cancelled) {
                              this.state.status  = "FUTURE BOOKING";
                              bookingAmount = 0;
                              this.state.buttonEnable = true;
                            }
                            sum += bookingAmount;

                             return(<tbody>
                                       <tr>
                                          <td >{booking.id}</td>
                                          <td>{this.state.propertySelected} </td>
                                          <td >{booking.check_in_date}</td>
                                          <td >{booking.check_out_date}</td>
                                          <td >{bookingAmount}</td>
                                          <td >{this.state.status }</td>
                                          <td><button onClick= {() => this.cancelProperty(booking)}>CANCEL</button></td>
                                          </tr>
                                    </tbody>);
                                  })  : ''
                        }
                  </table>
                  <select name="month-dropdown"
                              className="property-id-dropdown"
                              defaultValue = "Select Property Id"
                              onChange={(event)=>{this.state.propertySelected = event.target.value ; this.fetchBillingDetails()}}
                              >
                              <option value="">Select Property ID</option>
                              {options}
                  </select>
                  <select name="month-dropdown"
                          className="month-type-dropdown"
                          onChange={(event)=>{this.state.month = event.target.value; this.fetchBillingDetails()}}
                          >
                          <option value="">Select the billing month</option>
                          <option value="January">January</option>
                          <option value="February">February</option>
                          <option value="March">March</option>
                          <option value="April">April</option>
                          <option value="May">May</option>
                          <option value="June">June</option>
                          <option value="July">July</option>
                          <option value="August">August</option>
                          <option value="September">September</option>
                          <option value="October">October</option>
                          <option value="November">November</option>
                          <option value="December">December</option>
                    </select>
                  <h5 className = "host-dashboard-header"> Total money earned from Property {this.state.propertySelected} : {sum} </h5>
                  <Link to='/userDashboard' className="return-back"><u>Go to User Dashboard </u></Link>
              </div>

          </div>
      );
  }
}
export default HostDashboard;
