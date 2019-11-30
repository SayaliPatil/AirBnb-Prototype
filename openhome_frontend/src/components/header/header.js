import React,{ Component } from 'react';
import Nav from 'react-bootstrap/lib/Nav'
import './../../images/home.css';
import Navbar from 'react-bootstrap/lib/Navbar'
import NavItem from 'react-bootstrap/lib/NavItem'
import NavDropdown from 'react-bootstrap/lib/NavDropdown'
import MenuItem  from 'react-bootstrap/lib/NavItem'
import { connect } from 'react-redux';
import { Button} from 'react-bootstrap';
import homeawaylogo from './../../images/HomeAway_logo.png';
import homeicon from './../../images/homeicon.png';
import Login from './../Signup/login';
import OwnerLogin from './../Signup/ownerlogin';

class Header extends Component {
  constructor(props){
    super(props);
    this.state={
      loginClicked:false,
      ownerloginClicked: false
    }
    this.loginHandler=this.loginHandler.bind(this);
    this.ownerloginHandler=this.ownerloginHandler.bind(this)
  }
loginHandler=(event)=> {
  event.preventDefault();
  this.state.loginClicked=true;
  this.props.updateLogging(this.state.loginClicked);
}
ownerloginHandler=(event)=> {
  event.preventDefault();
  this.state.ownerloginClicked=true;
  this.props.updateOwnerLogging(this.state.ownerloginClicked);
}
    render() {
      {
        this.state.loginClicked!=false?<Login/>:''
      }
        return (

            <div className="header-div">
            <Navbar inverse collapseOnSelect className="home-navbar">

              <Navbar.Header>
                <h1 className="logo-image"> HomeAway </h1>

              </Navbar.Header>
              <Navbar.Collapse>
              <Nav pullRight>
                  <NavDropdown eventKey={3} title="Help" id="basic-nav-dropdown">
                    <MenuItem eventKey={3.1}>Visit Help Center</MenuItem>
                    <MenuItem eventKey={3.2}>Travelers</MenuItem>
                    <MenuItem eventKey={3.3}>How it works</MenuItem>
                    <MenuItem eventKey={3.3}>Security Center</MenuItem>
                  </NavDropdown>
                  <NavItem eventKey={2} href="#">
                      <Button className="header-btn">List Your Property</Button>
                  </NavItem>
                  <NavItem eventKey={4} href="#">
                  <img className="logo-home" src={homeicon}/>
                  </NavItem>
                </Nav>
                <Nav pullRight>
                  <NavDropdown eventKey={3} title="Login" id="basic-nav-dropdown">
                    <MenuItem eventKey={3.1} onClick={this.loginHandler} >Traveler Login </MenuItem>
                    <MenuItem eventKey={3.2} onClick={this.ownerloginHandler} >Owner Login</MenuItem>
                  </NavDropdown>
                </Nav>

              </Navbar.Collapse>
            </Navbar>
            </div>
        );
    }
}



export default Header;
