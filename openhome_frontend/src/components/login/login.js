import React,{ Component } from 'react';
import './login.css';
import { GOOGLE_CLIENT_ID, FACEBOOK_APP_ID } from './../../components/Configs/Configs.js';
import GoogleLogin from 'react-google-login';
import FacebookLogin from 'react-facebook-login';
import { Route, Redirect,withRouter } from 'react-router-dom';
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';
import {history} from "./../../utils/util";
import {BASE_URL} from './../../components/Configs/Configs.js';
import axios from 'axios';

class Login extends Component {
	constructor(props) {
		super(props);
		this.state = {
			email: "",
			password: "",
			authFlag : false,
			redirectVar: ''
		};
    this.submitLogin = this.submitLogin.bind(this);
	}
	componentWillMount() {
		this.setState({
			authFlag : false
		})
	}
	submitLogin = (e) => {
		var headers=new Headers();
		e.preventDefault();
		const data = {
			email : this.state.email,
			password : this.state.password
		}
		if(VALIDATION.fieldValidity("Password",this.state.password) && VALIDATION.emailValidity(this.state.email)){

			fetch(`http://localhost:8080/api/login`, {
             method: 'POST',
             mode: 'cors',
             headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
             body: JSON.stringify(data)
           }).then(response => {
              console.log("Status Code : ",response);
              if(response.status==200) {
								this.setState({
	 							 authFlag : true
	 						 })
	 						 alert("User logged in successfully");
							 history.push('/home');
		 					window.location.reload();
              return response.json();
            }
						else if(response.status==404) {
	 						alert("User not registered, Please sign up");
	 						history.push('/signup');
	 						window.location.reload();
	 						this.setState({
	 							authFlag : false
	 						})
	 				 }
	 				 else if(response.status==400) {
	 					 alert("Please entered incorrect email and password");
	 					 window.location.reload();
	 					 this.setState({
	 						 authFlag : false
	 					 })
	 				 }
          }).then(result => {
            console.log("Login details Results:",result);
						UTIL.saveUserDetails(result);
          })
			.catch(error => {
				console.log("Error : " + error);
				window.location.reload();
			});
		}
	}
	checkVerification(data) {
		var email = data.email;
		console.log("checkVerification email : " +email);
		axios.get(BASE_URL + '/api/oauthverified/' +email)
     .then((response) => {
        console.log("response", response)
        if(response.status == 200)  {
					alert("user logged in successfully");
					console.log("Response from server : " +response);
					UTIL.saveUserDetails(response.data);
					history.push('/home');
					window.location.reload();
        }
				else if(response.status == 400) {
					alert("First verify the account used to login to facebook or google");
				}
				else if(response.status == 404) {
					alert("First register with facebook or google to login into it");
				}
    }).catch(error => {
			console.log("Error : " + error);
			alert("Either user is not registered or verified to login with facebook or google")
		});
	}
	render() {
		const responseGoogle = (response) => {
		            console.log("Response received from google: " +JSON.stringify(response));
		            this.checkVerification(response.profileObj);
		          }
		const responseFacebook = (response) => {
						  console.log("Response received from facebook: " +JSON.stringify(response));
							this.checkVerification(response);
						}
		return(
			<div>
			{
				this.state.authFlag!=false? <Redirect to='/home'/>:''
			}
				<div className="login-div">
						<h3> Log in to OpenHome </h3>
					<div className="account-class">
					<h3> Need an account ?
					 	<a href="signup" className="header-img-class"> Sign Up</a>
					 </h3>
					 </div>
						<form className="form-class">
							<table className="login-table-class">
						      <tr><h3 className="header-login-class"> Account Login </h3></tr>
						      <hr className= "signup-horizontal"></hr>
						      <tr> <input type="text" className="txt-class-login" placeholder="Email Address" onChange={(event) => {this.setState({email : event.target.value})}}/> </tr>
						      <tr> <input type="password" className="txt-class-login" placeholder="Password" onChange={(event) => {this.setState({password : event.target.value})}}/></tr>
						      <a href="#" className="forgot-password"> Forgot Password </a><br></br>
						      <button onClick = {this.submitLogin} className="btn btn-primary login">Login</button>
						       <h4 className="horizontal-line"> <span>or</span> </h4>
									 <br></br>
									 <div className = "oauth-login">
									 <FacebookLogin
													 appId={FACEBOOK_APP_ID}
													 fields="name,email,picture"
													 scope="public_profile"
													 callback={responseFacebook}
									/>
									<br></br>
									 <GoogleLogin
											 clientId={GOOGLE_CLIENT_ID}
											 buttonText="LOGIN WITH GOOGLE"
											 onSuccess={responseGoogle}
											 onFailure={responseGoogle}
											 cookiePolicy={'single_host_origin'}
											 className = "google-button-login"
									/>
									</div>
									<br></br>
			             <p className="signup-footer">We dont post anything without your permission.</p>
							</table>
						</form>
					</div>
			</div>
			);
	}
}

export default Login;
