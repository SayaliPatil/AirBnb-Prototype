import React,{ Component } from 'react';
import './payment.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import {Redirect} from 'react-router';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';
import {Link} from 'react-router-dom';

class BookingSuccess extends Component {
  constructor(props){
    super(props);
    this.state = {
      bookingDetails : {},
      redirectVar:'',
    }
    this.carddetail ={}
  }
  componentWillMount() {
    let state = this.props.location.state;
    this.setState({
      bookingDetails : state.bookingDetails
    })

  }
  componentDidMount() {
          fetch(`http://localhost:8080/api/book/email`, {
             method: 'POST',
             mode: 'cors',
             headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
             body: JSON.stringify(this.state.bookingDetails)
           }).then(response => {
              console.log("Status Code : ",response);
              if(response.status==200) {
                return response.json();
            }
          }).then(result => {
            console.log("Booking Results:",result);
          })
  }
  render() {
    console.log("Booking details on success page : " +this.state.bookingDetails);
    return (
            <div className = "booking-success">
                  <h2>Booking Confirmed</h2>
                  <hr className="success-horizontal"/>
                  <img src= "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS64E0AKohrm1aNLoqKq0tuYAw96Etr5W4OsEWL1uTQhvO80ljs&s" className="success-photo"/>
                  <h3 className ="success-header">  Booking confirmation sent to {this.state.bookingDetails.user_email} </h3>
                  <br></br>
                  <br></br>
                  <h5 className ="success-footers">Thanks for booking with OpenHome</h5>
                  <br></br>
                  <h5 className ="success-footers">Booking id : {this.state.bookingDetails.id} </h5>
                  <br></br>
                  <img src= "https://www.kaedu.co.in/wp-content/uploads/2017/06/image.png" className="waiting-photo"/>
                  <h5 className ="success-footers">Booking Dates are from {this.state.bookingDetails.check_in_date.split("T")[0]} to  {this.state.bookingDetails.check_out_date.split("T")[0]}</h5>
                  <br></br>
                  <h5 className ="success-footers">Total amount paid is : $ {this.state.bookingDetails.price}</h5>
                  <br></br>
                  <Link to='/home'><button className="btn btn-primary" style={{width:250}}>Go to HomePage</button> </Link>
            </div>
           );
  }
}
export default BookingSuccess;
