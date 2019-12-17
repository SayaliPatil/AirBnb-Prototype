import React,{ Component } from 'react';
import './dashboard.css';

import {history} from "./../../utils/util";
import Header from './../header/header.js';
import {Link} from 'react-router-dom';
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';

class UserBillingSummary extends Component {
  constructor(props){
    super(props);
    this.state={
        bookingDetails1:'',
        bookingID:'',
        totalPricing:'',
        inVoice:'',
        email:UTIL.getUserDetails(),
        startdate:'',
        enddate:'',
        accomodation:'',
      }
    this.carddetail ={}
  }
  render() {
    // console.log("Props passed : " +this.props.location.state.detail)
    var sum = 0;
    return (
      <div >
            <Header/>
            <br/>
             <div className="guest-billing-info">
                    <h2 className="dashboard-guest-heading"> Guest Monthly Billing Summary for : {this.props.location.state.detail.month}</h2>
                    <table className="table table-hover">
                        <thead className="active">
                          <tr>
                          <th >Booking ID</th>
                          <th >Property ID</th>
                          <th>Booking Start Date</th>
                          <th>Booking End Date</th>
                          <th >Booking Amount</th>
                          <th >Total Amount Paid</th>
                          </tr>
                        </thead>
                        {
                            this.props.location.state.detail != undefined ?
                              this.props.location.state.detail.map((booking) => {
                                sum += booking.amount_paid;
                               return(<tbody>
                                         <tr>
                                            <td >{booking.id}</td>
                                            <td >{booking.propertyId}</td>
                                            <td >{booking.check_in_date}</td>
                                            <td >{booking.check_out_date}</td>
                                            <td >{booking.price * booking.total_nights}</td>
                                            <td >{booking.amount_paid}</td></tr>
                                      </tbody>);
                                    })  : ''
                          }
                    </table>
                    <h5 className = "dashboard-header"> Total amount paid by guest : {sum} </h5>
                    <Link to='/userDashboard' className="return-back"><u>Go to User Dashboard </u></Link>
                </div>

            </div>
    );
  }
}
export default UserBillingSummary;
