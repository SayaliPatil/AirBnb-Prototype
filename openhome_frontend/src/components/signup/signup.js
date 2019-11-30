import React,{ Component } from 'react';
import axios from 'axios';
import { Route, Redirect,withRouter } from 'react-router-dom';
import { GOOGLE_CLIENT_ID, FACEBOOK_APP_ID } from './../../components/Configs/Configs.js';
import './../login/login.css';
import GoogleLogin from 'react-google-login';
import FacebookLogin from 'react-facebook-login';
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';

class SignUp extends Component {
	constructor(props) {
		super(props);
		this.state = {
			firstName: "",
			lastName: "",
			email: "",
			password: "",
			authFlag : false,
			user_role :''
		};
    this.submitLogin = this.submitLogin.bind(this);
	}
	componentWillMount() {
		this.setState({
			authFlag : false
		})
	}
	submitLogin = (e) => {
		e.preventDefault();

		if(VALIDATION.emailValidity(this.state.email)){
				const data = {
					first_name : this.state.firstName,
					last_name : this.state.lastName,
					email : this.state.email,
					password : this.state.password,
					verified : false
				}
				if(this.state.email.includes("sjsu"))	{
						data.user_role = "Host";
				}
				else {
					data.user_role = "Guest";
				}
				fetch(`http://localhost:8080/api/signup`, {
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
							 alert("Verification mail has been sent. Please verify before login.!!")
							 window.location.reload();
					 }
					 else if(response.status==302) {
						 	alert("User is already registered with same email id");
							window.location.reload();
	 						this.setState({
	 							authFlag : false
	 						})
					 }
				})
				.catch(error => {
					console.log("Error : " + error);
					alert("User registeration failed because of sever error")
				});
			}
	}
	oauthLogin(response) {
				this.setState({
					first_name : response.name.split(" ")[0],
					last_name : response.name.split(" ")[1],
					email : response.email,
					password : ""
				});
				if(this.state.email.includes("sjsu"))	{
						this.state.user_role = "Host";
				}
				else {
					this.state.user_role = "Guest";
				}
				this.state.oauth_flag = true;
				fetch(`http://localhost:8080/api/signup`, {
					 method: 'POST',
					 mode: 'cors',
					 headers: { ...UTIL.getUserHTTPHeader(),'Content-Type': 'application/json' },
					 body: JSON.stringify(this.state)
				 }).then(response => {
					 console.log("Status Code : ",response);
					 if(response.status==200) {
							 this.setState({
								 authFlag : true
							 })
							 alert("Verification mail has been sent. Please verify before login.!!")
							 window.location.reload();
					 }
					 else if(response.status==302) {
							alert("User is already registered with same email id");
							window.location.reload();
							this.setState({
								authFlag : false
							})
					 }
				})
				.catch(error => {
					console.log("Error : " + error);
					alert("User registeration failed because of sever error")
				});
	}

	render() {
		const responseGoogle = (response) => {
		            console.log("Response received from google: " +JSON.stringify(response));
								this.oauthLogin(response.profileObj);
		          }
		const responseFacebook = (response) => {
						  console.log("Response received from facebook: " +JSON.stringify(response));
							this.oauthLogin(response);
						}
		return(
			<div>
				<div className="login-div">
					<h4> Sign up for OpenHome </h4>
					<div className="account-class">
					<h3> Already have an account ?
					 	<a href="login" className="header-img-class"> Log in</a>
					 </h3>
					</div>
						<form className="form-class1">
						<table className="signup-table-class">
						      <tr><h3 className="header-signup-class"> Account Login </h3></tr>
						      <hr className= "signup-horizontal"></hr>
						      <tr>
						      		<td><input type="text" className="small-field1" placeholder="First Name" onChange={(event) => {this.setState({firstName : event.target.value})}} required/> </td>
						      		<td> <input type="text" className="small-field2" placeholder="Last Name" onChange={(event) => {this.setState({lastName : event.target.value})}} required/></td>
						      </tr>
						      <tr> <td><input type="text" className="large-field" placeholder="Email Address" onChange={(event) => {this.setState({email : event.target.value})}} required/></td> </tr>
						      <tr > <td><input type="password" className="large-field" placeholder="Password" onChange={(event) => {this.setState({password : event.target.value})}} required/></td></tr>
						      <button onClick = {this.submitLogin} className="btn btn-primary">Sign Me Up</button>
						      <h5 className="signup-horizontal-line"> <span>or</span> </h5>
									<FacebookLogin
													textButton="Sign up with Facebook"
													appId={FACEBOOK_APP_ID}
													fields="name,email,picture"
													scope="public_profile"
													callback={responseFacebook}
								 />
								 <br></br>
									<GoogleLogin
											clientId={GOOGLE_CLIENT_ID}
											buttonText="SIGNUP WITH GOOGLE"
											onSuccess={responseGoogle}
											onFailure={responseGoogle}
											cookiePolicy={'single_host_origin'}
											className = "google-button-signup"
								 />
			            <p className="signup-footer">We dont post anything without your permission.</p>
							</table>
						</form>
					</div>
			</div>
			);
	}
}

export default SignUp;
