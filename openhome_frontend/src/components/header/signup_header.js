import React,{ Component } from 'react';
import { Nav, Navbar, NavItem } from 'react-bootstrap'
import './../images/login.css';
import { connect } from 'react-redux';
import { Button} from 'react-bootstrap';
import homeawaylogo from './../images/HomeAway_logo.png';
import homeicon from './../images/homeicon.png';



class SignUpHeader extends Component {
    render() {
        return (
            <div >
            <Navbar inverse collapseOnSelect className="home-navbar">
              <Navbar.Header>
                <Navbar.Brand>
                  <img className="home-logo1" src ={homeawaylogo}/>
                </Navbar.Brand>
              </Navbar.Header>
            </Navbar>
            <Nav pullRight>
                  <NavItem eventKey={2} href="#">
                    <img className="logo-home1" src={homeicon}/>
                  </NavItem>
              </Nav>
            </div>
        );
    }
}



export default SignUpHeader;
