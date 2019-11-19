import React,{ Component } from 'react';
import './../../images/login.css';
import facebookicon from './../../images/facebookicon.jpg';
import googleicon from './../../images/googleicon.jpg';
import { Button,Modal,Checkbox } from 'react-bootstrap';
import axios from 'axios';
// import cookie from 'react-cookies';
import { Route, Redirect,withRouter } from 'react-router-dom';
import * as UTIL from './../../utils/util';
import * as VALIDATION from './../../utils/validation';

class Login extends Component {
	constructor(props) {
		super(props);
		this.state = {
			username: "",
			password: "",
			authFlag : false
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
			username : e.target.value
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
			username : this.state.username,
			password : this.state.password
		}
		axios.default.withCredentials=true;
		if(VALIDATION.validateField("Password",this.state.password) && VALIDATION.validateEmail(this.state.username)){
		axios.post('http://localhost:8080/api/login', data)
			.then(response => {
				console.log("Status Code : ",response);
				if(response.status==200) {
					this.setState({
						authFlag : true
					})
					var userdata=response.data.userID;
					var servertoken=response.data.servertoken;

					console.log("server token:", response.userID);
					UTIL.saveServerToken(userdata, servertoken);
				}
				else {
					alert("User not registered, Please sign up");
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
						<h3> Log in to OpenHome </h3>
					<div className="account-para">
					<h3> Need an account ?
					 	<a href="signup" className="header-img"> Sign Up</a>
					 </h3>
					 </div>
					<div className="form-login">
						<form className="form-group">
							<table className="login-table">
						      <tr><h3 className="header-login1"> Account Login </h3></tr>
						      <hr></hr>
						      <tr> <input type="text" className="txt-field" placeholder="Email Address" onChange = {this.usernameChangeHandler}/> </tr>
						      <tr> <input type="password" className="txt-field" placeholder="Password" onChange= {this.passwordChangeHandler}/></tr>
						      <a href="#" className="forgot-pwd1"> Forgot Password </a><br></br>
						      <button onClick = {this.submitLogin} className="btn btn-primary login">Login</button>
						       <h4 className="horizontal-line"> <span>or</span> </h4>
			                   <button className="fb-button1" ><img className="fb-image" src ={facebookicon} />Log in with Facebook</button><br></br>
			                   <button className="google-button1"><img className="fb-image" src ={googleicon}/>Log in with Google</button>
			                   <p className="footer">We dont post anything without your permission.</p>
							</table>
						</form>
					</div>
					</div>
			</div>
			);
	}
}

export default Login;
