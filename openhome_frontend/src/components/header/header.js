import React,{ Component } from 'react';
import './header.css';
import  { NavbarBrand, Navbar, NavbarNav, NavItem, Dropdown,DropdownMenu,DropdownItem,DropdownToggle,NavbarToggler,Collapse } from 'mdbreact';
import * as UTIL from './../../utils/util';
import {history} from './../../utils/util';
import { Route, Redirect,withRouter } from 'react-router-dom';
import { Button} from 'react-bootstrap';
import TimeAdvancement from './../TimeAdvancement/timeAdvancement.js';

class Header extends Component {
    constructor(props){
        super(props);
        this.state = {
            time: new Date().toLocaleString(),
            timeClicked : false,
        };
        let currentUser = UTIL.getUserDetails();
        let first_name = UTIL.getUserFirstName();
      }

  componentDidMount() {
        this.intervalID = setInterval(
          () => this.tick(),
          1000
        );
      }

  componentWillUnmount() {
        clearInterval(this.intervalID);
      }

  tick() {
        this.setState({
          time: new Date().toLocaleString()
        });
  }

  guestDashBoardHandler(data) {
      if(UTIL.getUserRole() == 'Guest') {
        history.push('/userDashboard');
      }
      else{
        alert("Login first to view booking details");
      }
  }
  guestCheckinHandler(data) {
      if(UTIL.getUserRole() == 'Guest') {
        history.push('/checkin');
      }
      else{
        alert("Login first to check-in/check-out/cancel booking");
      }
  }
  timeAdvancementClickHandler() {
    // this.setState({
    //   timeClicked : true,
    // })
    var email = UTIL.getUserDetails();
    if(email == undefined || email == null || email.length == 0) {
      alert("Login first to advance the time");
      history.push('/login');

    }
    else{
      history.push('/timeAdvancement');
    }

    window.location.reload();
  }

  hostDashboardHandler() {
      if(UTIL.getUserRole() == 'Host') {
        history.push('/hostDashboard');
      }
      else {
        alert("First Login as Owner")
      }
  }
  postPropertyHandler() {
    if(UTIL.getUserRole() == 'Host') {
      history.push('/postProperty');
    }
    else {
      alert("First Login as Owner")
    }
  }

  editPropertyHandler() {
    if(UTIL.getUserRole() == 'Host') {
      history.push('/editDashboard');
    }
    else {
      alert("First Login as Owner")
    }
  }
  logoutHandler(){
    alert("User logged out");
    UTIL.deleteUserDetails();
    history.push('/login');

  }
  buttonToggle() {
    history.push('/home');
    window.location.reload();
  }

  componentWillMount() {
    this.setState({
      currentuser: UTIL.getUserDetails()
    })
  this.currentUser = UTIL.getUserDetails();
  this.firstName = UTIL.getUserFirstName();
  }

renderFuntion() {
    if(this.state.timeClicked) {
      return <TimeAdvancement/>
    }
  }
render() {
 console.log("this.currentUser : " +this.currentUser);
        return (

                <div className="header-main">
                  <Navbar dark black expand="md" scrolling className="main-nav">
                    {<NavbarToggler onClick = { this.onClick } />}
                    <Collapse isOpen = { this.state.collapse } navbar>
                    <NavbarBrand>
                      <h1 onClick={()=> this.buttonToggle(this.currentUser)} className="brand"> OpenHome </h1>
                    </NavbarBrand>
                    <NavbarNav right>
                    <NavItem>
                          <p className="time-class">{this.state.time}</p>

                    </NavItem>
                    <NavItem>
                        <Dropdown>
                            <DropdownToggle nav caret id="basic-nav-dropdown"  className="menu-class">Guest</DropdownToggle>
                                <DropdownMenu>
                                  <DropdownItem href="#" onClick={() => this.guestCheckinHandler(this.currentUser)} >My Trips</DropdownItem>
                                  <DropdownItem href="#" onClick={() => this.guestDashBoardHandler(this.currentUser)}>Guest Dashboard</DropdownItem>
                                  <DropdownItem href="/login" onClick={() => this.logoutHandler()} >Logout</DropdownItem>
                                </DropdownMenu>
                            </Dropdown>
                    </NavItem>
                      <NavItem>
                        <Dropdown>
                          <DropdownToggle nav caret  id="basic-nav-dropdown">Host</DropdownToggle>
                              <DropdownMenu>
                              <DropdownItem href="#" onClick={() => this.postPropertyHandler()} >Post property</DropdownItem>
                              <DropdownItem href="#" onClick={() => this.editPropertyHandler(this.currentUser)} >Edit Property</DropdownItem>
                              <DropdownItem href="#" onClick={() => this.hostDashboardHandler(this.currentUser)} >Host Dashboard</DropdownItem>
                              <DropdownItem href="/login" onClick={() => this.logoutHandler()} >Logout</DropdownItem>
                              </DropdownMenu>
                            </Dropdown>
                      </NavItem>
                      <NavItem>
                            {this.currentUser == null ? <Button type="button" className="btn btn-secondary timeadvance" onClick={() => this.props.history.push('/login')}>Login &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</Button> : ''}
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Button type="button" className="btn btn-secondary timeadvance" onClick={() => this.timeAdvancementClickHandler(this.currentUser)}>Time Advancement</Button>
                            {this.state.timeClicked == true ? this.renderFuntion() : ''}
                      </NavItem>

                      </NavbarNav>
                    </Collapse>
                  </Navbar>
                </div>
        );
    }
}

export default Header;
