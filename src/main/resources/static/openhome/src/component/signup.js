import React,{ Component } from 'react';
import axios from 'axios';
import { Route, Redirect,withRouter } from 'react-router-dom';
import './../images/login.css';
//import SignUpHeader from './signup_header.js';
import facebookicon from './../images/facebookicon.jpg';
import googleicon from './../images/googleicon.jpg';
import { Button,Modal,Checkbox } from 'react-bootstrap';
import * as UTIL from './../utils/util';
import * as VALIDATION from './../utils/validation';

class SignUp extends Component {
	constructor(props) {
		super(props);
		this.state = {
			firstName: "",
			lastName: "",
			email: "",
			password: "",
			authFlag : false
		};
		this.emailChangeHandler = this.emailChangeHandler.bind(this);
    this.passwordChangeHandler = this.passwordChangeHandler.bind(this);
  	this.firstnameChangeHandler= this.firstnameChangeHandler.bind(this);
    this.lastnameChangeHandler = this.lastnameChangeHandler.bind(this);
    this.submitLogin = this.submitLogin.bind(this);
	}
	componentWillMount() {
		this.setState({
			authFlag : false
		})
	}
	firstnameChangeHandler = (e) => {
		this.setState({
			firstName : e.target.value
		})
	}
	lastnameChangeHandler = (e) => {
		this.setState({
			lastName : e.target.value
		})
	}
	emailChangeHandler = (e) => {
		this.setState({
			email : e.target.value
		})
	}
	passwordChangeHandler = (e) => {
		this.setState ({
			password : e.target.value
		})
	}
	submitLogin = (e) => {
		var headers=new Headers();
		e.preventDefault();

		axios.default.withCredentials=true;
		if(VALIDATION.validateField("Password",this.state.password) && VALIDATION.validateEmail(this.state.email) && VALIDATION.validateName(this.state.firstName) && VALIDATION
			.validateName(this.state.lastName)){
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
			axios.post('http://localhost:8080/api/signup', data)
				.then(response => {
					console.log("Status Code : ",response);
					if(response.status==200) {
						this.setState({
							authFlag : true
						})
						var userdata=response.data.userID;
						var servertoken=response.data.servertoken;
						UTIL.saveServerToken(userdata, servertoken)
					}
					else {
						this.setState({
							authFlag : false
						})
					}
				});
			}
	}
	render() {
		return(
			<div>
				{
					this.state.authFlag!=false? <Redirect to='/'/>:''
				}

				<div className="home-login">
					<div  className="sign-main">
					<h4> Sign up for HomeAway </h4>
					<div className="account-para">
					<h3> Already have an account ?
					 	<a href="login" className="header-img"> Log in</a>
					 </h3>
					</div>
					</div>
					<div className="form-login">

						<form className="form-group1">
						<table className="login-table">
						      <tr><h3 className="header-login"> Account Login </h3></tr>
						      <hr></hr>
						      <tr>
						      		<td><input type="text" className="txt-field-small1" placeholder="First Name" onChange = {this.firstnameChangeHandler}/> </td>
						      		<td> <input type="text" className="txt-field-small2" placeholder="Last Name" onChange = {this.lastnameChangeHandler}/></td>
						      </tr>
						      <tr> <td><input type="text" className="txt-field-lg" placeholder="Email Address" onChange = {this.emailChangeHandler}/></td> </tr>
						      <tr > <td><input type="password" className="txt-field-lg" placeholder="Password" onChange = {this.passwordChangeHandler}/></td></tr>
						      <button onClick = {this.submitLogin} className="btn btn-primary">Sign Me Up</button>
						      <h5 className="horizontal-line"> <span>or</span> </h5>
			                   <button className="fb-button" onClick={this.signup}><img className="fb-image" src ={facebookicon} />Log in with Facebook</button>
			                   <button className="google-button" onClick={this.signup}><img className="fb-image" src ={googleicon}/>Log in with Google</button>
			                   <p className="footer">We don't post anything without your permission.</p>
							</table>
						</form>

					</div>
					</div>
			</div>
			);
	}
}



export default SignUp;
