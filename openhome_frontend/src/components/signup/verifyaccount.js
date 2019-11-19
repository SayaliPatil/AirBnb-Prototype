import React,{ Component } from 'react';
import axios from 'axios';
import { Route, Redirect,withRouter } from 'react-router-dom';
// import AlertContainer from 'react-alert';
// import { options } from './utils/util.js';
import * as UTIL from './../../utils/util';

class VerifyAccount extends Component {

  verifyAccount = ((data)=>{
    const headers = {
            'Accept': 'application/json'
          };
    console.log(data);
    fetch(`http://localhost:8080/api/verifyaccount?ID=${data}`, {
             method: 'GET',
             mode: 'cors',
             headers: { ...headers,'Content-Type': 'application/json' }
           }).then(response => {
              console.log("Status Code : ",response);
              if(response.status==200) {
                console.log("Status Code in if: ",response);
                // UTIL.displayAlert("User account has been verified Successfully","success", this);
                this.props.history.push('/login');
            }
            else if(response.status==400) {
              console.log("Status Code in else if: ",response);
              // UTIL.displayAlert("User account has already been verified","warning", this);
              this.props.history.push('/login');
            }
            else {
              console.log("Status Code in else: ",response);
              // UTIL.displayAlert("User can not be verified because of server error","error", this);
              this.props.history.push('/login');
            }
          });
        });
	render() {
    console.log("I am in verify account page");
		return(
			<div>
          <div>
              {this.verifyAccount(this.props.match.params.ID)}
          </div>
			</div>
			);
	}
}



export default VerifyAccount;
