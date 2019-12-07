import React, {Component} from 'react';
import {Route} from 'react-router-dom';
import Home from './Home/Home';
import Properties from './Properties/Properties';
import Booking from './Booking/Booking';
import SignUp from './signup/signup.js';
import Login from './login/login.js';
import VerifyAccount from './signup/verifyaccount';
import PaymentDetails from './Payment/paymentDetails';
import ListProperty from "./PostProperty/ListProperty";

import PlaceOrder from './Payment/placeOrder';
import BookingSuccess from './Payment/bookingSuccess';
import Checkin from './CheckInOut/checkin';
import UserDashboard from './dashboard/userDashboard';
import HostDashboard from './dashboard/hostDashboard';
import UserBillingSummary from './dashboard/userBillingSummary';
import EditDashboard from './EditProperty/editDashboard';
import EditProperty from './EditProperty/EditProperty';

class Main extends Component {
    render(){
        return(
            <div>
                <Route path="/home" component={Home}/>
                <Route path="/properties" component={Properties}/>
                <Route path="/booking" component={Booking}/>
                <Route path="/paymentDetails" component={PaymentDetails}/>
                <Route path="/placeOrder" component={PlaceOrder}/>
                <Route path="/bookingSuccess" component={BookingSuccess}/>
                <Route path="/userDashboard" component={UserDashboard}/>
                <Route path="/hostDashboard" component={HostDashboard}/>
                <Route  exact path="/signup" render ={() => (<SignUp/>)}/>
    		        <Route  exact path="/login" render ={() => (<Login/>)}/>
                <Route  exact path="/checkin" render ={() => (<Checkin/>)}/>
    		        <Route  path="/verifyaccount/:ID" render ={(match) => (<VerifyAccount {...match} />)} />
                <Route path="/postProperty" component={ListProperty}/>
                <Route path="/userBillingSummary" component={UserBillingSummary}/>
        	<Route path="/editDashboard" component={EditDashboard}/>
                <Route path="/editProperty/:id" component={EditProperty}/>    
	     </div>
        )
    }
}
//Export The Main Component
export default Main;
