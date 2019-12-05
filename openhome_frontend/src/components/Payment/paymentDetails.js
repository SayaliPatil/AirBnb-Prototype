import React,{ Component } from 'react';
import './payment.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import {Redirect} from 'react-router';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';

class PaymentDetails extends Component {
  constructor(props){
    super(props);
    this.state = {
      bookingDetails : {},
      redirectVar:'',
    }
    this.carddetail ={}
  }

  checkCreditDataValid(data){
    if(VALIDATION.creditCardValidity(data.cardNumber) && VALIDATION.cvvNumberValidity(data.cvvNumber)){
        return true;
    }
    return false;
  }

  validateStateAndZipCode(data){
    if(data.state && !VALIDATION.stateValidity(data.state))
        return false;
    if(data.zip && !VALIDATION.zipCodeValidity(data.zip))
        return false;
    if(data.city && !VALIDATION.cityValidity(data.city))
        return false;
    if(data.nameOnCard && !VALIDATION.nameValidity(data.nameOnCard))
        return false;
    if(data.cardType && !VALIDATION.creditCardTypeValidity(data.cardType))
        return false;
    return true;
  }

 handleCardAdd(data){
   var email= UTIL.getUserDetails();
   if(email){
       if(VALIDATION.emailValidity(email) && this.checkCreditDataValid(data) && this.validateStateAndZipCode(data)){
         const payload = {
               email : email,
               nameOnCard : data.nameOnCard,
               cardNumber : data.cardNumber,
               cardType : data.cardType,
               cvv : data.cvvNumber,
               address : data.address,
               city : data.city,
               state : data.state,
               zip : data.zip,
               userID : UTIL.getUserIdDetails()
            }
            fetch(`${BASE_URL}/api/addcard`, {
    					 method: 'POST',
    					 mode: 'cors',
    					 headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
    					 body: JSON.stringify(payload)
    				 }).then(response => {
    					 console.log("Status Code : ",response);
    					 if(response.status==200) {
                  console.log("Response got : " +response);
                  alert("Card added successfully.!!")
                  window.location.reload();
    					 }
    				})
    				.catch(error => {
    					console.log("Error : " + error);
    					alert("Card addtion failed because of sever error")
    				});
    			}
       }
       else{
         alert("User is not logged in..!!!");
       }
   }

  render() {
    return (
            <div className = "add-payment-main">
                <div className = "add-payment-class">
                  <table className = "cardadd-class">
                  <tr><h2>Add Payment Detail</h2>
                  <hr className="payment-horizontal"/>

                  </tr>
                  <tr> <td><label>Name on Card</label> </td>
                  <td><label>Card Number</label> </td></tr>
                  <tr>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(nameOnCard) => {this.carddetail.nameOnCard = nameOnCard.target.value}} placeholder="Name on Card" size="45"/>
                  </td>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(cardNumber) => {this.carddetail.cardNumber = cardNumber.target.value}}  placeholder="Card Number" size="40"/>
                  </td>
                  </tr>
                  <br></br>
                  <tr>
                    <td><label>Card Type</label></td>
                    <td><label>CVV</label></td>
                  </tr>
                  <tr>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(cardType) => {this.carddetail.cardType = cardType.target.value}}  placeholder="Card Type" size="40"/>
                  </td>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(cvvNumber) => {this.carddetail.cvvNumber = cvvNumber.target.value}}  placeholder="CVV" size="40"/>
                  </td>
                  </tr>
                  <br></br>
                  <tr>
                  <td><label>Address</label></td>
                  <td><label>City</label></td>
                  </tr>
                  <tr>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(address) => {this.carddetail.address = address.target.value}}  placeholder="Address" size="40"/>
                  </td>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(city) => {this.carddetail.city = city.target.value}}  placeholder="City" size="40"/>
                  </td>
                  </tr>
                  <br></br>
                  <tr>
                  <td><label>State</label></td>
                  <td><label>Zip</label></td>
                  </tr>
                  <tr>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(state) => {this.carddetail.state = state.target.value}}  placeholder="State" size="40"/>
                  </td>
                  <td><input type="text" style={{width:400}} className="form-control" onChange={(zip) => {this.carddetail.zip = zip.target.value}}  placeholder="Zip" size="40"/>
                  </td></tr>

                  <tr><button onClick= {() => this.handleCardAdd(this.carddetail)} type="submit" className="btn btn-primary" style={{width:150}}>Add</button>
                  </tr>
                  </table>
                </div>



            </div>
           );
  }
}
export default PaymentDetails;
