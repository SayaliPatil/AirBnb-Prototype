import React,{ Component } from 'react';
import './payment.css';
import {history} from "./../../utils/util";
import {Link} from 'react-router-dom';
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';
import {bindActionCreators} from 'redux';

class OrderSummary extends Component {
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
  // componentWillMount() {
  //   const state = this.props.order;
  //   console.log("Propperty details on order summary page : " +state.address);
  // }
  render() {
    let imagesArray = this.props.order.images.split(";");
    let value = imagesArray[0];
    let imageURL = "https://image.flaticon.com/icons/svg/32/32441.svg"
    var date1 = new Date(this.props.order.startdate );
    var date2 = new Date(this.props.order.enddate);
    var total_nights = (date2.getTime() - date1.getTime()) / (1000 * 3600 * 24);
    console.log("totalDays: " +total_nights);
    if(total_nights == 0) {
        total_nights = 1;
    }
    console.log("this.props.order.beds : " +this.props.order.beds);
    return (
      <div className= "order-summary">
            <table className="static-border">
                <tr className= "headline-class">
                    <td > {this.props.order.description}</td>
                    <td> <img src={value} className="order-summary-photo"/> </td>
                </tr>
                <br></br>
                <hr className= "horizontal"/>
                <tr>
                  <td> <img src={imageURL} className="photo-class"/>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{this.props.order.beds} &nbsp;&nbsp;{this.props.order.beds <= 1 ? "Guest" : "Guests"}  </td>
                </tr>
                <br></br>
                <br></br>
                <tr className = "date-class">
                  <td> <img src="http://icons.iconarchive.com/icons/icons8/windows-8/512/Business-Overtime-icon.png" className="photo-class"/>  &nbsp;&nbsp;&nbsp;{this.props.order.startdate.split("T")[0]}
                  &nbsp;&nbsp;&nbsp;<img src="https://dejpknyizje2n.cloudfront.net/svgcustom/clipart/preview/arrow-pointing-right-or-left-up-or-down-2617-13878-300x300.png" className="arrow-class"/></td> <td>&nbsp;&nbsp;{this.props.order.enddate.split("T")[0]} </td>
                </tr>
                <br></br>
                <hr className= "arrow-horizontal"/>
                <br></br>
                <tr>
                  <th> $ {(this.props.order.price)} / night </th>
                  <th> {total_nights > 1 ? total_nights + "nights" : total_nights + " night"}   </th>
                </tr>
                <br></br>
              <tr>
                <td>Total (USD)</td>
                <th className="txt-field-price"> $ {(this.props.order.price) * (this.props.order.beds) * total_nights} </th>
              </tr>
              <tr>
                <td> Includes taxes and fees </td>
                <td><a href="#"> View Details </a></td>
              </tr>
              <br></br>
              <hr className= "horizontal"/>
              <br></br>
              <br></br>
            </table>
            <div className = "agreement"> By placing your order, you agree to Openhome privacy notice and conditions of use. </div>
      </div>
    );
  }
}
export default OrderSummary;
