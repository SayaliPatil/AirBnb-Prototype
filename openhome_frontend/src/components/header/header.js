import React,{ Component } from 'react';
import './header.css';
import  { NavbarBrand, Navbar, NavbarNav, NavItem, Dropdown,DropdownMenu,DropdownItem,DropdownToggle,NavbarToggler,Collapse } from 'mdbreact';
import * as UTIL from './../../utils/util';
import {history} from './../../utils/util';
import { Route, Redirect,withRouter } from 'react-router-dom';
import { Button} from 'react-bootstrap';

class Header extends Component {
    constructor(props){
        super(props);
        this.state = {
        };
        let currentUser = UTIL.getUserDetails();
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
  hostDashboardHandler() {
      if(UTIL.getUserRole() == 'Host') {
        history.push('/hostdashboard');
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
      history.push('/editProperty');
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
  }
    render() {

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
                        <Dropdown>
                            <DropdownToggle nav caret id="basic-nav-dropdown" >Guest</DropdownToggle>
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
                      </NavbarNav>
                    </Collapse>
                  </Navbar>
                </div>
        );
    }
}

export default Header;
