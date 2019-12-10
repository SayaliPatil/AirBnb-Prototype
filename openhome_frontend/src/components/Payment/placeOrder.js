import React,{ Component } from 'react';
import './payment.css';
import OrderSummary from './orderSummary.js';
import PaymentDetails from './paymentDetails.js';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';
import axios from 'axios';
import Header from './../header/header.js';

class PlaceOrder extends Component {
  constructor(props){
    super(props);
    this.state = {
      orderSummary : {},
      cardDetails: [],
      cardSelected : false,
      bookingDetails : {},
    }
    this.selectHandler = this.selectHandler.bind(this);
    this.clickHandler = this.clickHandler.bind(this);
  }
  componentWillMount() {
    const state = this.props.location.state;
    this.setState({
      orderSummary : state.detail
    })
    var email = UTIL.getUserDetails();
    var id = UTIL.getUserIdDetails();
    axios.get(BASE_URL + '/api/fetchcard/' + id)
     .then((response) => {
        console.log("response", response)
        if(response.status == 200)
        {
            this.setState({
              cardDetails : this.state.cardDetails.concat(response.data)
            })
        }
    });
  }
  selectHandler = (event) => {
    event.preventDefault();
    this.setState({
      cardSelected : true
    })
    alert("Card has been selected successfully.!!")
  }


  clickHandler=(event)=> {
    event.preventDefault();
    var start = new Date(this.state.orderSummary.userSelectedStartDate );
    var end = new Date(this.state.orderSummary.userSelectedEnddate);
    var total_nights = (end.getTime() - start.getTime()) / (1000 * 3600 * 24);
    console.log("total_nights : " +total_nights);
    if(total_nights == 0) {
      this.props.history.push({
          pathname: '/home'
      })
    }
    if(this.state.cardSelected) {
      var details={
        "propertyId":localStorage.getItem("propid"),
        "check_in_date":this.state.orderSummary.userSelectedStartDate,
        "check_out_date":this.state.orderSummary.userSelectedEnddate,
        "availabilty_start_date" : this.state.orderSummary.startdate.split("T")[0],
        "availabilty_end_date" : this.state.orderSummary.enddate.split("T")[0],
        "host_email":this.state.orderSummary.host_email,
        "price":this.state.orderSummary.weekdayprice * total_nights,
        "beds":this.state.orderSummary.beds,
        "user_checked_in_flag" : false,
        "user_email" : UTIL.getUserDetails(),
        "property_unique_id" : this.state.orderSummary.id,
        "total_nights" : total_nights,
        "headline" : this.state.orderSummary.headline,
      }
            fetch(`${BASE_URL}/api/book`, {
               method: 'POST',
               mode: 'cors',
               headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
               body: JSON.stringify(details)
             }).then(response => {
                console.log("Status Code : ",response);
                if(response.status==200) {
                  return response.json();
              }
            }).then(result => {
              console.log("Booking Results:",result);
                  this.setState({
                    bookingDetails:result,
                  });
                  this.props.history.push({
                      pathname: '/bookingSuccess',
                      state: { bookingDetails: result }
                  })
                  alert("Property booked successfully.!!")
            })
          }
    else {
      alert("Please select a saved card or add a card to pay");
      }
    }
  render() {
    var email = UTIL.getUserDetails();
    if(email == undefined || email == null || email.length == 0) {
      alert("Login to book property");
      this.props.history.push('/login');
    }
        let fetchCard = this.state.cardDetails.map(cardItem => {
            return (
                <div className="card-table">
                <div class="row">
                <div class="col-sm-20" className="backColor_dash_list">
                <ul class="list-inline">
                <li>Card Number : {cardItem.cardNumber}</li>
                <li>Name on Card: {cardItem.nameOnCard}</li>
                <li>Card Type : {(cardItem.cardType).toString()}</li>
                </ul>
                <button className="btn btn-primary select" type="submit" onClick={this.selectHandler}>Select</button>
                <ul class="list-inline">
                <li>CVV : {cardItem.cvv} </li>
                <li>Address : {cardItem.address} </li>
                </ul>
                </div>
                </div>
                </div>
              );
    });
    console.log("Order summary : " + this.state.orderSummary.property_unique_id);
    return (
            <div className = "add-payment-main">
            <Header/>
                <h1 className ="confirm-header"> Confirm and Pay </h1>
                <OrderSummary order = {this.state.orderSummary}/>
                <PaymentDetails/>
                <div className = "saved-card-class"> <h2 className = "save-header">Saved Cards</h2>
                  {fetchCard}
                </div>
                <tr> <button className="btn btn-primary order" type="submit" onClick={this.clickHandler}>Confirm Booking</button></tr>
            </div>
           );
  }
}
export default PlaceOrder;
