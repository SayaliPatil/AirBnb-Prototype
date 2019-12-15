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

class UserDashboard extends Component {
  constructor(props){
    super(props);
    this.state = {
      bookingDetails : [],
      current : 1,
      itemsPerPage : 2,
      activePage: 1,
      month:' ',
      monthSelected : false,
      monthlyBillingDetails : [],
    }
    this.clickHandler = this.clickHandler.bind(this);
  }
  fetchBillingDetails() {
    this.state.monthSelected = true;
    const data = {
			email : UTIL.getUserDetails(),
			month : this.state.month,
      id : ''
		}
    fetch(`${BASE_URL}/api/user/fetchBillingDetails/`, {
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
      console.log("Booking Results:",result);
      result.month = this.state.month
      this.props.history.push({
          pathname: '/userBillingSummary',
          state: { detail: result}
      })
    })
  }
  clickHandler(event) {
      this.setState({
          current: Number(event.target.id)
      });
    }
  componentDidMount() {
    var email = UTIL.getUserDetails();
    if(email) {
      axios.get(BASE_URL + '/api/fetchUserDashBoard/' + email)
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


  render() {
    console.log("Month Selected : " +this.state.month);
    const { current, itemsPerPage } = this.state;
    const indexOfLastPage = current * itemsPerPage;
    const indexOfFirstPage = indexOfLastPage - itemsPerPage;
    const currentTodos = this.state.bookingDetails.slice(indexOfFirstPage, indexOfLastPage);
    console.log("Number of properties : " + this.state.bookingDetails.length);
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(this.state.bookingDetails.length / itemsPerPage); i++) {
        pageNumbers.push(i);
    }
    const showPageNumbers = pageNumbers.map(number => {
        return (
          <li class="page-item active"
            key={number}
            id={number}
            onClick={this.clickHandler}
            className="nums"
          >
      {number}
          </li>
        );
      });

      let bookingInfo = currentTodos.map(bookingItem => {
      var cancelFlag = bookingItem.booking_cancelled == true ? true : false;
      var bookFlag = (bookingItem.user_checked_in_flag == false && bookingItem.user_checked_out_flag == false && bookingItem.booking_cancelled == false) ? true : false;
      var checkInFlag = (bookingItem.user_checked_in_flag == true && bookingItem.user_checked_out_flag == false && bookingItem.booking_cancelled == false) ? true : false;
      var checkOutFlag = (bookingItem.user_checked_in_flag == true && bookingItem.user_checked_out_flag == true && bookingItem.booking_cancelled == false) ? true : false;
      var stay = bookingItem.total_nights;
      return (
              <div className="dashboard-card-class">
                <div class="row">
                    <div class="col-sm-10">
                      <ul class="list-inline">
                        <li><i><u><h4><b>Booking ID : {bookingItem.id}</b></h4></u></i></li>
                        <br></br>
                        <li className = "font"><h5> "{ bookingItem.headline} " property owned by {bookingItem.host_email} </h5></li>
                        <li className = "font"><h5> {checkOutFlag ? <b>was checked out in past.</b>: ""}</h5></li>
                        <li className = "font"><h5>  {checkInFlag ? <b>is currently checked in. </b> : ""}</h5></li>
                        <li className = "font"><h5> {bookFlag? <b>has been booked for future. </b> : ""}</h5></li>
                        <li className = "font"><h5> {cancelFlag? <b>has been cancelled.</b> : ""}</h5></li>
                        <li className = "amount-class">{checkOutFlag? <h5> <b>Total Amount paid for {stay} nights of stay :  $ {bookingItem.price}</b> </h5>: ""}</li>
                        <li className = "amount-class"> {checkInFlag? <h5> <b>Total Amount paid for {stay} nights of stay :  $ {bookingItem.amount_paid} </b> </h5>: ""}</li>
                        <li className = "amount-class">{cancelFlag?  <h5> <b>Total Amount to pay after cancellation :  $ {bookingItem.amount_paid} </b></h5>: ""}</li>
                        <li className = "amount-class">{bookFlag? <h5><b> Total Amount to pay on checkin for {stay} nights of stay:  $ {bookingItem.price}</b> </h5>: ""}</li>

                        <li><h5> Booking on following dates : </h5></li>
                        <table>
                            <tr className = "date-class-dashboard">
                              <td> <img src="http://icons.iconarchive.com/icons/icons8/windows-8/512/Business-Overtime-icon.png" className="photo-class"/>  &nbsp;&nbsp;&nbsp;{bookingItem.check_in_date}
                              &nbsp;&nbsp;&nbsp;<img src="https://dejpknyizje2n.cloudfront.net/svgcustom/clipart/preview/arrow-pointing-right-or-left-up-or-down-2617-13878-300x300.png" className="arrow-class"/></td> <td>&nbsp;&nbsp;{bookingItem.check_out_date} </td>
                            </tr>
                        </table>

                      </ul>
                      {checkOutFlag? <img className = "past-successs-class" src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzzd1-8xL1x-3SFu9VfcSoRIR_gh9Krk7bXaFDKAP4QZvslKWc&s"/> : ""}
                      {checkInFlag? <img className = "checkin-successs-class" src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmmJXa7iL-S9z__tK-jOM_JDTh6WZgYKLueb73GkG6tFql7cB6&s"/> : ""}
                      {cancelFlag? <img className = "booking-cancel-class"src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSG45SaTyLluOx9p-uuzmZP7cSdbGfbvMyORwvePdkHL9GTuaFAIg&s"/> : ""}
                      {bookFlag? <img className = "booking-success-class"src = "https://image.flaticon.com/icons/svg/1487/1487294.svg"/> : ""}
                  </div>
                </div>
              </div>
            );
    });
    console.log("User booking details on dasboard page : " +this.state.bookingDetails);
    return (
              <div>
              <Header/>
              {this.state.month != ' ' ? <UserBillingSummary/> : <img className = "dashboard-image-class" src = "http://wowk.sd38.bc.ca/sites/wowk.sd38.bc.ca/files/pac/feature-images/2015/12/team_celebration_pc_3655.png"/>}
                  <div className="dashboard-header">
                      <h> Guest dashboard
                      </h></div>
                      <div>
                          {bookingInfo}
                      </div>
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

                      <div className="dashboard_pagi">
                      <nav aria-label="Page navigation example">
                      <ul class="pagination">
                      {showPageNumbers}
                      </ul>
                      </nav>
                  </div>
              </div>
           );
  }
}
export default UserDashboard;
