import React,{ Component } from 'react';
import './../Properties/Properties.css';
// import './dashboard.css';
import {history} from "./../../utils/util";
import * as UTIL from './../../utils/util';
import {Redirect} from 'react-router';
import * as VALIDATION from './../../utils/validation';
import {BASE_URL} from './../../components/Configs/Configs.js';
import {Link} from 'react-router-dom';
import axios from 'axios';
import Header from './../header/header.js';
// import UserBillingSummary from './userBillingSummary.js';

class EditDashboard extends Component {
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
    // this.cancelProperty = this.cancelProperty.bind(this);
  }
  fetchBillingDetails() {
    this.state.propertySelectedFlag = true;
    let id = this.state.propertySelected;
    this.props.history.push({
        pathname: '/editProperty/'+id,
    })
        // const data = {
        //   email : UTIL.getUserDetails(),
        //   month : this.state.month,
        //   id : this.state.propertySelected,
        // }
        // fetch(`${BASE_URL}/api/user/fetchHostDashBoard/`, {
        //    method: 'POST',
        //    mode: 'cors',
        //    headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
        //    body: JSON.stringify(data)
        //  }).then(response => {
        //     console.log("Status Code : ",response);
        //     if(response.status==200) {
        //       return response.json();
        //   }
        // }).then(result => {
        //   console.log("Host Dashboard Results:",result);
        //   this.setState({
        //     bookingDetails : result
        //   })
        // })

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

                  <select name="month-dropdown"
                              className="property-id-dropdown"
                              defaultValue = "Select Property Id"
                              onChange={(event)=>{this.state.propertySelected = event.target.value ; this.fetchBillingDetails()}}
                              >
                              <option value="">Select Property ID</option>
                              {options}
                  </select>
              </div>

          </div>
      );
  }
}
export default EditDashboard;
