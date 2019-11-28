import React,{ Component } from 'react';
import './login.css';
import facebookicon from './../../images/facebookicon.jpg';
import googleicon from './../../images/googleicon.jpg';
import axios from 'axios';
// import cookie from 'react-cookies';
import { Route, Redirect,withRouter } from 'react-router-dom';
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';
import {history} from "./../../utils/util";

class Login extends Component {
	constructor(props) {
		super(props);
		this.state = {
			email: "",
			password: "",
			authFlag : false,
			redirectVar: ''
		};
		this.usernameChangeHandler = this.usernameChangeHandler.bind(this);
    this.passwordChangeHandler = this.passwordChangeHandler.bind(this);
    this.submitLogin = this.submitLogin.bind(this);
	}
	componentWillMount() {
		this.setState({
			authFlag : false
		})
	}
	usernameChangeHandler = (e) => {
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
		const data = {
			email : this.state.email,
			password : this.state.password
		}
		if(VALIDATION.fieldValidity("Password",this.state.password) && VALIDATION.emailValidity(this.state.email)){

			fetch(`http://localhost:8080/api/login`, {
             method: 'POST',
             mode: 'cors',
             headers: { ...headers,'Content-Type': 'application/json' },
             body: JSON.stringify(data)
           }).then(response => {
              console.log("Status Code : ",response);
              if(response.status==200) {
								this.setState({
	 							 authFlag : true
	 						 })
	 						 alert("User logged in successfully");
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
						UTIL.saveServerToken(result.email , result.id );
          })
			.catch(error => {
				console.log("Error : " + error);
				window.location.reload();
			});
		}
	}
	render() {

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
						      <tr> <input type="text" className="txt-class-login" placeholder="Email Address" onChange = {this.usernameChangeHandler}/> </tr>
						      <tr> <input type="password" className="txt-class-login" placeholder="Password" onChange= {this.passwordChangeHandler}/></tr>
						      <a href="#" className="forgot-password"> Forgot Password </a><br></br>
						      <button onClick = {this.submitLogin} className="btn btn-primary login">Login</button>
						       <h4 className="horizontal-line"> <span>or</span> </h4>
			             <button className="facebook-button1" ><img className="fb-image" src ={facebookicon} />Log in with Facebook</button><br></br>
			             <button className="google-button1"><img className="fb-image" src ={googleicon}/>Log in with Google</button>
			             <p className="signup-footer">We dont post anything without your permission.</p>
							</table>
						</form>
					</div>
			</div>
			);
	}
}

export default Login;
