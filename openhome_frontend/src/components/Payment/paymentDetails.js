import React,{ Component } from 'react';
import './payment.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';
import {bindActionCreators} from 'redux';

class PaymentDetails extends Component {
  constructor(){
    super();
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
   var headers=new Headers();
   if(email){
       if(VALIDATION.emailValidity(email) && this.checkCreditDataValid(data) && this.validateStateAndZipCode(data)){
         const payload = {
               email : email,
               nameOnCard : data.nameOnCard,
               cardNumber : data.cardNumber,
               cardType : data.cardType,
               address : data.address,
               city : data.city,
               state : data.state,
               zip : data.zip
            }
            fetch(`http://localhost:8080/api/addcard`, {
    					 method: 'POST',
    					 mode: 'cors',
    					 headers: { ...headers,'Content-Type': 'application/json' },
    					 body: JSON.stringify(payload)
    				 }).then(response => {
    					 console.log("Status Code : ",response);
    					 if(response.status==200) {
                  console.log("Response got : " +response.data);
    					 }
    					 else if(response.status==400) {
    						 	alert("User entered wrong details or is not logged in");
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
         this.props.history.push("/login");
       }
   }

  render() {
    return (
            <div className = "add-payment-main">
                <div className = "add-payment-class">
                  <h2>Add Payment Detail</h2>
                  <hr/>
                  <label>Name on Card</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(nameOnCard) => {this.carddetail.nameOnCard = nameOnCard.target.value}} placeholder="Name on Card" size="45"/>
                  <br></br>
                  <label>Card Number</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(cardNumber) => {this.carddetail.cardNumber = cardNumber.target.value}}  placeholder="Card Number" size="40"/>
                  <br></br>
                  <label>Card Type</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(cardType) => {this.carddetail.cardType = cardType.target.value}}  placeholder="Card Type" size="40"/>
                  <br></br>
                  <label>CVV</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(cvvNumber) => {this.carddetail.cvvNumber = cvvNumber.target.value}}  placeholder="CVV" size="40"/>
                  <br></br>
                  <label>Address</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(address) => {this.carddetail.address = address.target.value}}  placeholder="Address" size="40"/>
                  <br></br>
                  <label>City</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(city) => {this.carddetail.city = city.target.value}}  placeholder="City" size="40"/>
                  <br></br>

                  <label>State</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(state) => {this.carddetail.state = state.target.value}}  placeholder="State" size="40"/>

                  <br></br>
                  <label>Zip</label>
                  <input type="text" style={{width:400}} className="form-control" onChange={(zip) => {this.carddetail.zip = zip.target.value}}  placeholder="Zip" size="40"/>

                  <br></br>

                  <button onClick= {() => this.handleCardAdd(this.carddetail)} type="submit" className="btn btn-primary" style={{width:150}}>Add</button>

                </div>



            </div>
           );
  }
}
export default PaymentDetails;
